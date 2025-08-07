import game.Game

fun main() {
    val game = Game(rows = 8, cols = 8, numMines = 10)
    game.start()
}