package game

class AiGameEngine(private val api: Api) {
    fun nextQuestion(history: List<QuestionAnswer>): Question {
        val response = api.generate(PromptBuilder().buildNextQuestionPrompt(history))
        return ResponseParser().parseQuestion(response)
    }

    fun tryGuess(history: List<QuestionAnswer>): Guess? {
        val response = api.generate(PromptBuilder().buildGuessPrompt(history))
        return ResponseParser().parseGuess(response)
    }
}
