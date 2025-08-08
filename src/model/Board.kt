package model

import kotlin.random.Random

/**
 * Represents the Minesweeper game board.
 * Handles mine placement, cell revealing, and game logic.
 */
class Board(private val rows: Int, private val cols: Int, private val numMines: Int) {
    private val grid: Array<Array<Cell>> = Array(rows) { row ->
        Array(cols) { col -> Cell(row, col) }
    }

    /**
     * Checks if coordinates are within the board bounds.
     */
    fun inBounds(x: Int, y: Int): Boolean = x in 0..<rows && y in 0..<cols

    /**
     * Randomly places mines on the board, avoiding the first clicked cell.
     */
    fun generateMines(excludeX: Int, excludeY: Int) {
        var placed = 0
        while (placed < numMines) {
            val x = Random.nextInt(rows)
            val y = Random.nextInt(cols)
            if ((x != excludeX || y != excludeY) && !grid[x][y].isMine) {
                grid[x][y].isMine = true
                placed++
            }
        }
    }

    /**
     * Calculates the number of adjacent mines for each cell.
     */
    fun calculateAdjacencies() {
        for (x in 0..<rows) {
            for (y in 0..<cols) {
                if (!grid[x][y].isMine) {
                    grid[x][y].adjacentMines = neighbors(x, y).count { it.isMine }
                }
            }
        }
    }

    /**
     * Returns the list of adjacent cells around a given coordinate.
     */
    private fun neighbors(x: Int, y: Int): List<Cell> {
        val offsets = listOf(-1, 0, 1)
        return offsets.flatMap { dx ->
            offsets.mapNotNull { dy ->
                if (dx == 0 && dy == 0) null
                else {
                    val nx = x + dx
                    val ny = y + dy
                    if (inBounds(nx, ny)) grid[nx][ny] else null
                }
            }
        }
    }

    /**
     * Reveals the cell at (x, y). Returns true if a mine was revealed.
     */
    fun revealCell(x: Int, y: Int): Boolean {
        val cell = grid[x][y]
        if (cell.isRevealed || cell.isFlagged) return false

        cell.isRevealed = true
        if (cell.isMine) return true

        if (cell.adjacentMines == 0) {
            neighbors(x, y).forEach { revealCell(it.x, it.y) }
        }
        return false
    }

    /**
     * Toggles the flag state of the cell at (x, y).
     */
    fun flagCell(x: Int, y: Int) {
        val cell = grid[x][y]
        if (!cell.isRevealed) cell.isFlagged = !cell.isFlagged
    }

    /**
     * Checks if all non-mine cells have been revealed.
     */
    fun isComplete(): Boolean {
        return grid.flatten().all {
            it.isRevealed || it.isMine
        }
    }

    /**
     * A red color function for the board
     */
    private fun red(text: String): String = "\u001B[31m$text\u001B[0m"

    /**
     * A green color function for the board
     */
    private fun green(text: String): String = "\u001B[32m$text\u001B[0m"

    /**
     * Prints the current state of the board to the console,
     * with each column occupying 2 spaces for lineament.
     * @param showMines Whether to reveal all mines
     */
    fun printBoard(showMines: Boolean = false) {
        print("   ")
        for (col in 0..<cols) {
            print(col.toString().padStart(2))
        }
        println()

        for (x in 0..<rows) {
            print(x.toString().padStart(2) + " ")
            for (y in 0..<cols) {
                val cell = grid[x][y]
                val symbol = when {
                    cell.isFlagged -> " ${red("F")}"
                    !cell.isRevealed && showMines && cell.isMine -> " ${red("X")}"
                    !cell.isRevealed -> " ${green("*")}"
                    cell.isMine -> " ${red("X")}"
                    cell.adjacentMines > 0 -> " ${cell.adjacentMines}"
                    else -> " #"
                }
                print(symbol)
            }
            println()
        }
    }
}
