package model

/**
 * Represents a single cell on the board.
 * @property x Row coordinate
 * @property y Column coordinate
 * @property isMine Indicates if the cell contains a mine
 * @property isRevealed Indicates if the cell has been revealed
 * @property isFlagged Indicates if the cell is flagged by the player
 * @property adjacentMines Number of mines adjacent to this cell
 */
data class Cell(
    val x: Int,
    val y: Int,
    var isMine: Boolean = false,
    var isRevealed: Boolean = false,
    var isFlagged: Boolean = false,
    var adjacentMines: Int = 0
)
