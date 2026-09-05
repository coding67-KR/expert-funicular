package game

enum class Answer(val label: String) {
    YES("예"),
    NO("아니오"),
    UNKNOWN("모르겠음");

    companion object {
        fun parse(input: String): Answer = when (input.trim().lowercase()) {
            "예", "네", "y", "yes", "ㅇ", "1" -> YES
            "아니오", "아니", "n", "no", "ㄴ", "2" -> NO
            else -> UNKNOWN
        }
    }
}
