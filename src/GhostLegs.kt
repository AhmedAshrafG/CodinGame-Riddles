import java.util.*

/**
 * Link: https://www.codingame.com/ide/puzzle/ghost-legs
 **/

typealias CharGrid = Array<CharArray>

typealias StateGrid = Array<BooleanArray>

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val W = input.nextInt()
    val H = input.nextInt()
    if (input.hasNextLine()) {
        input.nextLine()
    }

    val grid = CharGrid(H) { CharArray(W) }

    for (i in 0 until H) {
        val line = input.nextLine()
        grid[i] = line.toCharArray()
    }

    println(solve(grid, H, W))
}

fun solve(grid: CharGrid, H: Int, W: Int): String {
    return grid[0].mapIndexed { col, char ->
        if (char != ' ') {
            char.toString() + traverseGrid(
                    0,
                    col,
                    grid,
                    StateGrid(H) { BooleanArray(W) { false } }
            )
        } else null
    }
            .filterNotNull()
            .joinToString("\n")
}

fun traverseGrid(row: Int, col: Int, grid: CharGrid, visited: StateGrid): Char? {

    fun isValidMove(x: Int, y: Int) = x >= 0 && y >= 0 && x < grid.size && y < grid[x].size && !visited[x][y]

    fun getNextDirection(row: Int, col: Int): Direction {
        return when {
            isValidMove(row, col + 1) && grid[row][col + 1] != ' ' -> Direction.RIGHT
            isValidMove(row, col - 1) && grid[row][col - 1] != ' ' -> Direction.LEFT
            else -> Direction.DOWN
        }
    }

    val char = grid[row][col]
    visited[row][col] = true
    if (row == grid.size - 1)
        return char

    val nextDirection = getNextDirection(row, col)

    return traverseGrid(row + nextDirection.x, col + nextDirection.y, grid, visited)
}

enum class Direction(val x: Int, val y: Int) {
    DOWN(1, 0), RIGHT(0, 1), LEFT(0, -1)
}