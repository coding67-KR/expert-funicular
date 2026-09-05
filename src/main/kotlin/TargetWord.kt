package game

data class TargetWord(val value: String) {
    init {
        require(value.isNotBlank()) { "Target word cannot be blank." }
    }
}
