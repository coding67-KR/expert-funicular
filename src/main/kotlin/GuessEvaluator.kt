package game

object GuessEvaluator {
    fun isCorrect(guess: Guess, target: TargetWord): Boolean {
        if (guess.confidence < GameRules.MIN_CONFIDENCE) return false
        return normalize(guess.value) == normalize(target.value)
    }

    private fun normalize(value: String): String =
        value.trim().lowercase().replace(Regex("\\s+"), "")
}
