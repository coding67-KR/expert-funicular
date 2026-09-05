package game

class QuestionGenerator(private val client: GeminiClient) {
    fun generate(history: List<QuestionAnswer>): Question {
        val raw = client.generate(PromptBuilder().buildNextQuestionPrompt(history))
        return ResponseParser().parseQuestion(raw)
    }
}
