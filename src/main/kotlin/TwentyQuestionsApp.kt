package game

class TwentyQuestionsApp(
    private val ui: ConsoleUI = ConsoleUI(),
    private val gameFactory: GameFactory = GameFactory()
) {
    fun run() {
        ui.showWelcome()
        val target = ui.readTargetWord()
        val game = gameFactory.create(target)
        game.start()
        ui.showGoodbye()
    }
}
