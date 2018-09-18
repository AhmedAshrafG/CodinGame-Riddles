import java.util.*
import kotlin.collections.HashMap

/**
 * Link: https://www.codingame.com/ide/puzzle/the-river-ii-
 **/

fun main(args : Array<String>) {
    val input = Scanner(System.`in`)
    val num = input.nextInt()

    print(if (solve(num)) "YES" else "NO")
}

fun solve(target: Int): Boolean {
    val visited = HashMap<Int,Boolean?>()
    val state = HashMap<Int,Boolean>()
    var count = 0

    fun canReach(initial: Int, target: Int): Boolean? {
        var num = initial
        while(true) {
            if (visited[num] == true)
                return state[num]

            visited[num] = true

            if (num == target)
                return true.also { state[num] = it }

            if (num > target)
                return false.also { state[num] = it }

            num = accumulateDigits(num)
        }
    }

    (1..target+1).forEach {
        count += if (canReach(it, target) == true) 1 else 0
        if (count > 1)
            return true
    }
    return false
}

fun accumulateDigits(num: Int): Int {
    return num + num.toString().sumBy { it.toString().toInt() }
}
