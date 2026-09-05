package game

class GameFactory {
    fun create(targetWord: String): TwentyQuestionsGame {
        val target = TargetWord(targetWord)
        return TwentyQuestionsGame(
            target = target,
            ai = AiGameEngine(GeminiClient.fromEnvironment()),
            ui = ConsoleUI(),
            state = GameState()
        )
    }
}
