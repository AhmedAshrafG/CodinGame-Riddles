import java.util.*

/**
 * Link: https://www.codingame.com/ide/puzzle/hidden-word
 **/

fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val n = input.nextInt()

    val words = mutableListOf<String>()
    for (i in 0 until n) {
        val word = input.next()
        words += word
    }

    val h = input.nextInt()
    val w = input.nextInt()
    val grid = Array(h) { CharArray(w) }
    for (i in 0 until h) {
        val line = input.next()
        grid[i] = line.toCharArray()
    }
    // Write an action using println()
    // To debug: System.err.println("Debug messages...");

    println(solve(grid, words))
}

fun solve(grid: Array<CharArray>, words: List<String>): String {
    val state = Array(grid.size) {
        it -> BooleanArray(grid[it].size) { false }
    }
    words.forEach { word ->
        val wordCoordinates: List<Pair<Int,Int>> = getWordCoordinates(word, grid)!!
        wordCoordinates.forEach {
            state[it.first][it.second] = true
        }
    }
    val flatGrid = grid.flatMap(CharArray::asIterable)
    val flatState = state.flatMap(BooleanArray::asIterable)
    return flatGrid
            .asSequence()
            .mapIndexed { index, char ->
                if (flatState[index]) null else char
            }
            .filterNotNull()
            .joinToString("")
}

fun getWordCoordinates(word: String, grid: Array<CharArray>): List<Pair<Int, Int>>? {
    for (row in 0 until grid.size) {
        for (col in 0 until grid[row].size) {
            val coordinates = tryToStrikeWord(word, grid, row, col)
            if (coordinates != null)
                return coordinates
        }
    }
    return null
}

fun tryToStrikeWord(word: String, grid: Array<CharArray>, row: Int, col: Int): List<Pair<Int, Int>>? {
    val directions =  arrayOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
            1 to -1,
            -1 to 1,
            1 to 1,
            -1 to -1
    )
    directions.forEach { (dx, dy) ->
        val coordinates = tryToStrikeWordInDirection(word, grid, row, col, dx, dy, mutableListOf())
        if (coordinates != null)
            return coordinates
    }
    return null
}

fun tryToStrikeWordInDirection(word: String, grid: Array<CharArray>, row: Int, col: Int, directionX: Int, directionY: Int, coordinates: MutableList<Pair<Int, Int>>): List<Pair<Int, Int>>? {
    if (word.isEmpty())
        return coordinates

    if (!hasMatch(grid, word[0], row, col))
        return null

    coordinates += row to col
    return tryToStrikeWordInDirection(word.drop(1), grid, row + directionX, col + directionY, directionX, directionY, coordinates)
}

fun hasMatch(grid: Array<CharArray>, char: Char, x: Int, y: Int): Boolean {
    return x >= 0 && y >= 0 && x < grid.size && y < grid[x].size && grid[x][y] == char
}
