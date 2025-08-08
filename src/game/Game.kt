package game

import model.Board
import utils.Command
import utils.parseInput

/**
 * Controls the game loop and user interactions.
 * @property rows Number of rows on the board
 * @property cols Number of columns on the board
 * @property numMines Number of mines on the board
 */
class Game(private val rows: Int, private val cols: Int, private val numMines: Int) {
    private val board = Board(rows, cols, numMines)
    private var firstMove = true
    private var running = true

    /**
     * Starts the game loop, handling input and displaying the board.
     */
    fun start() {
        println("""
        Welcome to Minesweeper!
        
        How to Play:
          - Your goal is to reveal all cells that do NOT contain mines.
          - Use the commands below to interact with the board.
        
        Commands:
          r x y  - Reveal the cell at row x, column y (e.g., r 3 5)
          f x y  - Toggle a flag 🚩 on the cell at row x, column y (e.g., f 2 4)""".trimIndent()
        )
        while (running) {
            board.printBoard()
            print("Move (r x y to reveal, f x y to flag): ")
            val input = readlnOrNull()
            handleInput(input)
        }
    }

    /**
     * Handles user input and updates game state accordingly.
     * @param input The user input string
     */
    private fun handleInput(input: String?) {
        when (val command = parseInput(input)) {
            is Command.Reveal -> {
                val (x, y) = command
                if (!board.inBounds(x, y)) {
                    println("❌ Invalid coordinates!")
                    return
                }
                if (firstMove) {
                    board.generateMines(x, y)
                    board.calculateAdjacencies()
                    firstMove = false
                }
                val hitMine = board.revealCell(x, y)
                if (hitMine) {
                    board.printBoard(showMines = true)
                    println("💥 Boom! You stepped on a mine. Game over.")
                    running = false
                } else if (board.isComplete()) {
                    board.printBoard(showMines = true)
                    println("🎉 Congratulations! You won the game.")
                    running = false
                }
            }

            is Command.Flag -> {
                val (x, y) = command
                if (!board.inBounds(x, y)) {
                    println("❌ Invalid coordinates!")
                    return
                }
                board.flagCell(x, y)
            }

            Command.Invalid -> {
                println("⚠️ Invalid command. Please use `r x y` to reveal or `f x y` to flag.")
            }
        }
    }
}