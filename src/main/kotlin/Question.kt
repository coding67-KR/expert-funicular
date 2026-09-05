package game

data class Question(
    val text: String,
    val purpose: QuestionPurpose = QuestionPurpose.GENERAL
)

enum class QuestionPurpose {
    CATEGORY,
    PROPERTY,
    LOCATION,
    FUNCTION,
    GENERAL
}
