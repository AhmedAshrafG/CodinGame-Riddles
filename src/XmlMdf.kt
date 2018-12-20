import java.util.*
import kotlin.collections.HashMap

/**
 * Link: https://www.codingame.com/ide/puzzle/xml-mdf-2016
 **/
fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val sequence = input.nextLine()

    println(solve(sequence))
}

fun solve(sequence: String): String {
    val map = HashMap<Char, Double>()
    val stack = Stack<Char>()

    sequence.forEach {
        if (!stack.isEmpty() && stack.peek() == '-') {
            stack.pop()
            val currentValue = map.getOrDefault(it, 0.0)
            map[it] = currentValue + 1.0/stack.size
            stack.pop()
        } else {
            stack.push(it)
        }
    }
    return map.maxBy { it.value }!!
            .key
            .toString()
}