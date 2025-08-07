# Minesweeper in Kotlin

## Project Overview

This is a simple console-based implementation of the classic **Minesweeper** game written in Kotlin. 

The project aims to refresh Kotlin programming skills while practicing principles such as encapsulation, data classes, and modular code design.

Furthermore, it was a good opportunity  to learn how to create my own git repository with proper documentation, something I'll have to look for in the future.

---

## How to Play

- The game is played on a grid with hidden mines.
- Your goal is to reveal all cells **without** hitting a mine.
- You can **reveal** cells or **flag** cells you suspect contain mines.
- Commands are entered in the terminal:

r x y - Reveal the cell at row x, column y

f x y - Toggle a flag on the cell at row x, column y

- Rows and columns start from 0.
- Example commands:

r 3 5 # Reveals the cell at row 3, column 5

f 2 4 # Flags/unflags the cell at row 2, column 4


- The game ends when you reveal a mine (loss) or reveal all safe cells (win).

---

## Project Structure

    src/

    ├── Main.kt # Entry point that starts the game

       └── game/
            └── Game.kt # Controls the game loop and input handling

       └── model/
            └── Board.kt # Represents the Minesweeper board and game logic
            └── Cell.kt # Represents a single cell on the board
    
       └── utils/
            └── InputUtils.kt # Parses and validates user input commands


---

## Classes and Responsibilities

### `Main.kt`
- Creates and starts a new `Game` instance.

### `game.Game`
- Manages the game loop.
- Prints the board and user instructions.
- Handles user input by parsing commands and interacting with the board.
- Tracks game state (running, won, lost).

### `model.Board`
- Initializes a 2D grid of `Cell`s.
- Places mines randomly avoiding the first clicked cell.
- Calculates adjacent mine counts for each cell.
- Contains methods to reveal and flag cells.
- Checks if the game has been won.

### `model.Cell`
- Data class representing each board cell.
- Tracks coordinates, mine presence, revealed/flagged state, and adjacent mine count.

### `utils.InputUtils`
- Parses raw user input strings.
- Returns well-defined commands (Reveal, Flag, or Invalid).
- Helps keep input logic separated and testable.

---

## Design Decisions

- **Modularity:** Splitting code into clear packages and files helps maintainability and testing.
- **Data Class for Cells:** Allows easy state tracking and immutable data structures where possible.
- **First Move Safe:** Mines are generated *after* the first reveal to avoid immediate loss on first move.
- **Recursive Reveal:** If a revealed cell has zero adjacent mines, its neighbors are revealed automatically for smooth gameplay.
- **Console UI with Emojis:** Used emojis and clear symbols to make the board visually easier to understand in a terminal.
- **Input Parsing with Sealed Classes:** Simplifies command processing and future extensibility.
- **Zero-based Coordinates:** Chosen for programming consistency (standard Kotlin/Java style).
- **GameState Enum (Optional):** Could improve clarity of game states instead of a simple boolean flag.

---

## How to Run

1. Make sure you have Kotlin installed on your machine.
2. Compile and run `Main.kt`.
3. Follow the on-screen instructions to play the game.

---

## Possible Improvements

- Add difficulty levels.
- Add timer or move counter.
- Save/load game state.
- More user-friendly coordinate input (1-based indexing).
- Undo moves functionality.

