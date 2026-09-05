package game

class ResponseParser {
    fun parseQuestion(raw: String): Question {
        val cleaned = raw
            .replace("질문:", "")
            .replace("Question:", "")
            .trim()
        require(cleaned.isNotBlank()) { "AI returned an empty question." }
        return Question(cleaned)
    }

    fun parseGuess(raw: String): Guess? {
        val guess = Regex("GUESS\\s*=\\s*(.*?)(?:;|\\n|$)", RegexOption.IGNORE_CASE)
            .find(raw)?.groupValues?.get(1)?.trim()
        val confidence = Regex("CONFIDENCE\\s*=\\s*([0-9.]+)", RegexOption.IGNORE_CASE)
            .find(raw)?.groupValues?.get(1)?.toDoubleOrNull() ?: 0.0
        if (guess.isNullOrBlank() || guess.equals("NONE", ignoreCase = true)) return null
        return Guess(guess, confidence.coerceIn(0.0, 1.0))
    }
}
