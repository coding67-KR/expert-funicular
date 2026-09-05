package game

class HistoryFormatter {
    fun format(history: List<QuestionAnswer>): String = buildString {
        if (history.isEmpty()) {
            appendLine("아직 질문 기록이 없습니다.")
            return@buildString
        }
        history.forEachIndexed { index, item ->
            appendLine("${index + 1}. ${item.question.text} -> ${item.answer.label}")
        }
    }
}
