package game

class ConsoleUI {
    fun showWelcome() {
        println("=== AI 스무고개 ===")
        println("생각한 단어를 입력하면 Gemini가 질문으로 정답을 추리합니다.")
        println("답변: 예 / 아니오 / 모르겠음")
        println()
    }

    fun readTargetWord(): String {
        while (true) {
            print("생각한 단어: ")
            val value = readlnOrNull()?.trim().orEmpty()
            if (value.isNotBlank()) return value
            println("단어를 한 글자 이상 입력해 주세요.")
        }
    }

    fun showQuestion(question: Question, number: Int) {
        println("\n[$number/${GameRules.MAX_QUESTIONS}] AI 질문: ${question.text}")
    }

    fun readAnswer(): Answer {
        print("답변 > ")
        return Answer.parse(readlnOrNull().orEmpty())
    }

    fun showGuess(guess: Guess) {
        println("AI 추측: ${guess.value} (확신도 ${"%.0f".format(guess.confidence * 100)}%)")
    }

    fun showResult(state: GameState) {
        val resultText = when (state.result) {
            GameResult.WIN -> "🎉 AI가 정답을 맞혔습니다!"
            GameResult.OUT_OF_QUESTIONS -> "20번 안에 정답을 찾지 못했습니다."
            GameResult.ABORTED -> "게임이 중단되었습니다."
            null -> "게임이 종료되었습니다."
        }
        println("\n$resultText")
        println("질문 ${state.history.size}개 사용.")
    }

    fun showGoodbye() {
        println("게임 종료!")
    }
}
