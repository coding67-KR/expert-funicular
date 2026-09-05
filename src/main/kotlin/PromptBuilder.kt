package game

class PromptBuilder {
    fun buildNextQuestionPrompt(history: List<QuestionAnswer>): String = buildString {
        appendLine("너는 스무고개 AI다. 사용자가 생각한 대상을 맞히기 위해 한국어로 예/아니오로 답하기 쉬운 질문 하나만 만들어라.")
        appendLine("질문은 이미 했던 질문과 중복되면 안 된다.")
        appendLine("출력은 질문 문장 하나만 보내라.")
        appendLine("지금까지의 기록:")
        history.forEachIndexed { index, item ->
            appendLine("${index + 1}. ${item.question.text} -> ${item.answer.label}")
        }
    }

    fun buildGuessPrompt(target: TargetWord, history: List<QuestionAnswer>): String = buildString {
        appendLine("너는 스무고개 판정 AI다.")
        appendLine("정답 후보를 추론하고, 확실할 때만 추측하라.")
        appendLine("대상은 사용자가 입력한 비밀 단어이며, 이 값은 게임 엔진 내부 참고용이다: ${target.value}")
        appendLine("기록:")
        history.forEachIndexed { index, item ->
            appendLine("${index + 1}. ${item.question.text} -> ${item.answer.label}")
        }
        appendLine("출력 형식: GUESS=<추측>;CONFIDENCE=<0~1>")
        appendLine("확신이 부족하면 GUESS=NONE;CONFIDENCE=0 으로 출력하라.")
    }
}
