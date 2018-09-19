import java.util.*

/**
 * Link: https://www.codingame.com/ide/puzzle/bulk-email-generator
 **/
fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val N = input.nextInt()
    if (input.hasNextLine()) {
        input.nextLine()
    }
    val lines = mutableListOf<String>()
    for (i in 0 until N) {
        val line = input.nextLine()
        lines += line
    }
    println(solve(lines))
}

fun solve(lines: List<String>): String {
    var email = lines.joinToString("\n")

    val matches = "\\([\\s\\S]*?\\)".toRegex()
            .findAll(email)
            .map { it.value }
            .toList()
    matches.forEachIndexed { index, choice ->
        val clauses = choice.drop(1).dropLast(1).split("|")
        val clause = clauses[index%clauses.size]
        email = email.replaceFirst(choice, clause)
    }
    return email
}
