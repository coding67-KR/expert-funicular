package game

class QuestionGenerator(private val api: Api) {
    fun generate(history: List<QuestionAnswer>): Question {
        val raw = api.generate(PromptBuilder().buildNextQuestionPrompt(history))
        return ResponseParser().parseQuestion(raw)
    }
}
