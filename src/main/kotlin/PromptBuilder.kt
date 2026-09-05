package game

class PromptBuilder {
    fun buildNextQuestionPrompt(history: List<QuestionAnswer>): String = buildString {
        appendLine("너는 스무고개 AI다.")
        appendLine("사용자가 생각한 대상을 맞히기 위해 한국어로 예/아니오로 답하기 쉬운 질문 하나만 만들어라.")
        appendLine("질문은 이전 질문과 중복되면 안 된다.")
        appendLine("정답 단어를 직접 묻거나 사용자의 개인 정보를 묻지 마라.")
        appendLine("출력은 질문 문장 하나만 보내라.")
        appendLine("지금까지의 기록:")
        history.forEachIndexed { index, item ->
            appendLine("${index + 1}. ${item.question.text} -> ${item.answer.label}")
        }
    }

    fun buildGuessPrompt(history: List<QuestionAnswer>): String = buildString {
        appendLine("너는 스무고개 AI다.")
        appendLine("지금까지의 답변만으로 사용자가 생각한 대상을 추측하라.")
        appendLine("정답 단어는 너에게 공개되지 않는다.")
        appendLine("출력 형식: GUESS=<추측>;CONFIDENCE=<0~1>")
        appendLine("확신이 부족하면 GUESS=NONE;CONFIDENCE=0 으로 출력하라.")
        appendLine("기록:")
        history.forEachIndexed { index, item ->
            appendLine("${index + 1}. ${item.question.text} -> ${item.answer.label}")
        }
    }
}
