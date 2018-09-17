import java.util.*
/**
 * Link: https://www.codingame.com/ide/puzzle/the-river-i-
 **/

fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val num1 = input.nextLong()
    val num2 = input.nextLong()

    println(solve(num1, num2))
}

tailrec fun solve(num1: Long, num2: Long): Long {
    if (num1 == num2)
        return num1

    val min = minOf(num1, num2)
    val max = maxOf(num1, num2)

    return solve(accumulateDigits(min), max)
}

fun accumulateDigits(num: Long): Long {
    return num + num.toString().sumBy { it.toString().toInt() }
}
