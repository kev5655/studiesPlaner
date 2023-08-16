package utlis

fun generatePowersOfTwoDescending(length: Int): List<Int> {
    val powersOfTwoList = mutableListOf<Int>()

    var value = 1
    var count = 0

    while (count < length) {
        powersOfTwoList.add(value)
        value *= 2
        count++
    }
    return powersOfTwoList.reversed().toMutableList()
}

