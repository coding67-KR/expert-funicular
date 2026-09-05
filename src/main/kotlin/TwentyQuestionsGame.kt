package game

class TwentyQuestionsGame(
    private val target: TargetWord,
    private val ai: AiGameEngine,
    private val ui: ConsoleUI,
    private val state: GameState
) {
    fun start() {
        ui.showTarget(target)
        while (!state.isFinished) {
            val question = ai.nextQuestion(state.history)
            state.ask(question)
            ui.showQuestion(question, state.questionNumber)
            val answer = ui.readAnswer()
            state.record(question, answer)
            val guess = ai.tryGuess(target, state.history)
            if (guess != null) {
                ui.showGuess(guess)
                state.finish()
            }
        }
        ui.showResult(state)
    }
}
