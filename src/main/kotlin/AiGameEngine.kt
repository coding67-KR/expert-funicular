package game

class AiGameEngine(private val client: GeminiClient) {
    fun nextQuestion(history: List<QuestionAnswer>): Question {
        val prompt = PromptBuilder().buildNextQuestionPrompt(history)
        val response = client.generate(prompt)
        return ResponseParser().parseQuestion(response)
    }

    fun tryGuess(target: TargetWord, history: List<QuestionAnswer>): Guess? {
        val prompt = PromptBuilder().buildGuessPrompt(target, history)
        val response = client.generate(prompt)
        return ResponseParser().parseGuess(response)
    }
}
