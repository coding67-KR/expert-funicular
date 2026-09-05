package game

class AiGameEngine(private val api: Api) {
    fun nextQuestion(history: List<QuestionAnswer>): Question {
        val prompt = PromptBuilder().buildNextQuestionPrompt(history)
        return ResponseParser().parseQuestion(api.generate(prompt))
    }

    fun tryGuess(target: TargetWord, history: List<QuestionAnswer>): Guess? {
        val prompt = PromptBuilder().buildGuessPrompt(target, history)
        return ResponseParser().parseGuess(api.generate(prompt))
    }
}
