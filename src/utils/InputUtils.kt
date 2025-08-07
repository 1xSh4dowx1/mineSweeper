package utils

/**
 * Represents a parsed command from user input.
 */
sealed class Command {
    data class Reveal(val x: Int, val y: Int) : Command()
    data class Flag(val x: Int, val y: Int) : Command()
    object Invalid : Command()
}

/**
 * Parses user input into a Command object.
 * Expected formats:
 *   - "r x y" for reveal
 *   - "f x y" for flag
 *
 * @param input the raw user input string
 * @return a Command object representing the user's intention
 */
fun parseInput(input: String?): Command {
    val parts = input?.trim()?.split(" ") ?: return Command.Invalid
    if (parts.size != 3) return Command.Invalid

    val x = parts[1].toIntOrNull()
    val y = parts[2].toIntOrNull()

    if (x == null || y == null) return Command.Invalid

    return when (parts[0].lowercase()) {
        "r" -> Command.Reveal(x, y)
        "f" -> Command.Flag(x, y)
        else -> Command.Invalid
    }
}