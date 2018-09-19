import java.util.*
import kotlin.collections.HashMap

/**
 * Link: https://www.codingame.com/ide/puzzle/order-of-succession
 **/
fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val n = input.nextInt()

    val personMap = HashMap<String, Person>()
    var queen: Person? = null

    for (i in 0 until n) {
        val name = input.next()
        val parent = input.next()
        val birth = input.nextInt()
        val death = input.next()
        val religion = input.next()
        val gender = input.next()

        val person = Person(name, birth, religion, death)
        if (parent == "-") {
            queen = person
            personMap[name] = queen
        } else {
            val parentPerson = personMap[parent]!!
            val genderList =
                    if (gender == "M")
                        parentPerson.maleSiblings
                    else
                        parentPerson.femaleSiblings
            genderList.add(person)
            personMap[name] = person
        }
    }
    print(solve(queen!!, personMap))
}

fun solve(queen: Person, personMap: HashMap<String, Person>): String {
    val relationList = traverseRelationTree(queen, personMap)
    return relationList
            .asSequence()
            .filter { it.isValidToRule }
            .map { it.name }
            .joinToString("\n")
}

fun traverseRelationTree(parent: Person, personMap: HashMap<String, Person>): List<Person> {
    val siblings = parent.allSiblings

    val sortedValidSiblings = siblings
            .map { traverseRelationTree(it, personMap) }
            .flatMap { it }

    return listOf(parent) + sortedValidSiblings
}

data class Person(
        val name: String,
        val birth: Int,
        val religion: String,
        val death: String,
        val maleSiblings: MutableSet<Person> = personSet(),
        val femaleSiblings: MutableSet<Person> = personSet()
) {
    val allSiblings: Set<Person> get() = maleSiblings + femaleSiblings
    val isValidToRule: Boolean = death == "-" && religion.toLowerCase() == "anglican"
}

fun personSet(): MutableSet<Person> {
    return sortedSetOf(kotlin.Comparator { o1, o2 -> o1.birth - o2.birth })
}
