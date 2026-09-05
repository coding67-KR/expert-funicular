package game

class TwentyQuestionsGame(
    private val target: TargetWord,
    private val ai: AiGameEngine,
    private val ui: ConsoleUI,
    private val state: GameState
) {
    fun start() {
        while (!state.isFinished) {
            val question = ai.nextQuestion(state.history)
            state.ask(question)
            ui.showQuestion(question, state.questionNumber + 1)
            val answer = ui.readAnswer()
            state.record(question, answer)

            val guess = ai.tryGuess(state.history)
            if (guess != null) {
                ui.showGuess(guess)
                if (GuessEvaluator.isCorrect(guess, target)) {
                    state.finish(GameResult.WIN)
                }
            }
        }

        if (!state.isFinished) state.finish(GameResult.OUT_OF_QUESTIONS)
        ui.showResult(state)
    }
}
