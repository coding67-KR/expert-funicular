package game

class GameState {
    val history: MutableList<QuestionAnswer> = mutableListOf()
    var isFinished: Boolean = false
        private set

    var result: GameResult? = null
        private set

    val questionNumber: Int
        get() = history.size

    fun ask(question: Question) {
        require(history.size < GameRules.MAX_QUESTIONS) { "No more questions are allowed." }
    }

    fun record(question: Question, answer: Answer) {
        history += QuestionAnswer(question, answer)
        if (history.size >= GameRules.MAX_QUESTIONS && !isFinished) {
            finish(GameResult.OUT_OF_QUESTIONS)
        }
    }

    fun finish(gameResult: GameResult) {
        isFinished = true
        result = gameResult
    }
}
