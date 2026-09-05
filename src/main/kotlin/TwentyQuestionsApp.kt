package game

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import kotlin.concurrent.thread

class TwentyQuestionsApp(
    private val api: Api = Api.fromEnvironment()
) {
    private lateinit var frame: JFrame
    private lateinit var targetField: JTextField
    private lateinit var questionLabel: JLabel
    private lateinit var progressLabel: JLabel
    private lateinit var historyArea: JTextArea
    private lateinit var startButton: JButton
    private lateinit var answerPanel: JPanel

    private val history = mutableListOf<QuestionAnswer>()
    private var target = ""
    private var questionCount = 0

    fun run() {
        SwingUtilities.invokeLater { createAndShow() }
    }

    private fun createAndShow() {
        frame = JFrame("스무고개 AI")
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.minimumSize = Dimension(760, 600)
        frame.setLocationRelativeTo(null)

        val root = JPanel(BorderLayout(16, 16))
        root.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        val title = JLabel("AI 스무고개", SwingConstants.CENTER).apply {
            font = Font("SansSerif", Font.BOLD, 30)
        }
        root.add(title, BorderLayout.NORTH)

        val center = JPanel(BorderLayout(12, 12))

        val setup = JPanel(BorderLayout(8, 8))
        setup.border = BorderFactory.createTitledBorder("정답 설정")
        targetField = JTextField()
        targetField.toolTipText = "AI가 맞혀야 할 단어를 입력하세요"
        startButton = JButton("게임 시작").apply { addActionListener { startGame() } }
        setup.add(targetField, BorderLayout.CENTER)
        setup.add(startButton, BorderLayout.EAST)
        center.add(setup, BorderLayout.NORTH)

        val gamePanel = JPanel(BorderLayout(12, 12))
        gamePanel.border = BorderFactory.createTitledBorder("게임")

        val status = JPanel(GridLayout(2, 1, 4, 4))
        progressLabel = JLabel("질문 0 / 20")
        questionLabel = JLabel("단어를 입력하고 게임을 시작하세요.")
        questionLabel.font = Font("SansSerif", Font.BOLD, 22)
        status.add(progressLabel)
        status.add(questionLabel)
        gamePanel.add(status, BorderLayout.NORTH)

        answerPanel = JPanel(GridLayout(1, 3, 10, 10))
        addAnswerButton("예", Answer.YES)
        addAnswerButton("아니오", Answer.NO)
        addAnswerButton("모르겠음", Answer.UNKNOWN)
        gamePanel.add(answerPanel, BorderLayout.SOUTH)

        historyArea = JTextArea().apply {
            isEditable = false
            lineWrap = true
            wrapStyleWord = true
            font = Font("SansSerif", Font.PLAIN, 15)
        }
        gamePanel.add(JScrollPane(historyArea), BorderLayout.CENTER)
        center.add(gamePanel, BorderLayout.CENTER)

        root.add(center, BorderLayout.CENTER)
        frame.contentPane = root
        setGameEnabled(false)
        frame.isVisible = true
    }

    private fun addAnswerButton(text: String, answer: Answer) {
        val button = JButton(text)
        button.addActionListener { submitAnswer(answer) }
        answerPanel.add(button)
    }

    private fun startGame() {
        val entered = targetField.text.trim()
        if (entered.isBlank()) {
            targetField.requestFocus()
            return
        }

        target = entered
        history.clear()
        questionCount = 0
        historyArea.text = ""
        progressLabel.text = "질문 0 / 20"
        questionLabel.text = "AI가 첫 질문을 준비하는 중..."
        startButton.isEnabled = false
        targetField.isEnabled = false
        setGameEnabled(false)
        requestQuestion()
    }

    private fun requestQuestion() {
        thread(isDaemon = true) {
            try {
                val question = AiGameEngine(api).nextQuestion(history)
                SwingUtilities.invokeLater {
                    questionCount++
                    questionLabel.text = question.text
                    progressLabel.text = "질문 $questionCount / ${GameRules.MAX_QUESTIONS}"
                    setGameEnabled(true)
                }
            } catch (ex: Exception) {
                showError(ex.message ?: "AI 요청 중 오류가 발생했습니다.")
                resetGame()
            }
        }
    }

    private fun submitAnswer(answer: Answer) {
        if (questionCount <= 0 || questionCount > GameRules.MAX_QUESTIONS) return

        val questionText = questionLabel.text
        val question = Question(questionText)
        history += QuestionAnswer(question, answer)
        historyArea.append("Q${history.size}. $questionText\nA. ${answer.label}\n\n")
        setGameEnabled(false)

        thread(isDaemon = true) {
            try {
                val guess = AiGameEngine(api).tryGuess(history)
                if (guess != null && guess.value.isNotBlank() && guess.value.uppercase() != "NONE") {
                    SwingUtilities.invokeLater {
                        questionLabel.text = "AI의 추측: ${guess.value}"
                    }
                    val localMatch = GuessEvaluator().matches(TargetWord(target), guess)
                    if (localMatch) {
                        SwingUtilities.invokeLater { finishWin(guess.value) }
                        return@thread
                    }
                }

                if (history.size >= GameRules.MAX_QUESTIONS) {
                    SwingUtilities.invokeLater { finishLose() }
                } else {
                    requestQuestion()
                }
            } catch (ex: Exception) {
                showError(ex.message ?: "AI 요청 중 오류가 발생했습니다.")
                resetGame()
            }
        }
    }

    private fun finishWin(guess: String) {
        questionLabel.text = "🎉 정답! $guess"
        historyArea.append("AI가 정답을 맞혔습니다!\n정답: $target\n")
        resetControlsAfterGame()
    }

    private fun finishLose() {
        questionLabel.text = "20번의 질문이 끝났습니다."
        historyArea.append("정답: $target\nAI가 이번에는 맞히지 못했습니다.\n")
        resetControlsAfterGame()
    }

    private fun resetControlsAfterGame() {
        setGameEnabled(false)
        startButton.isEnabled = true
        targetField.isEnabled = true
    }

    private fun resetGame() {
        setGameEnabled(false)
        startButton.isEnabled = true
        targetField.isEnabled = true
        progressLabel.text = "질문 0 / 20"
        questionLabel.text = "단어를 입력하고 게임을 시작하세요."
    }

    private fun setGameEnabled(enabled: Boolean) {
        for (component in answerPanel.components) {
            component.isEnabled = enabled
        }
    }

    private fun showError(message: String) {
        SwingUtilities.invokeLater {
            questionLabel.text = "오류: $message"
        }
    }
}
