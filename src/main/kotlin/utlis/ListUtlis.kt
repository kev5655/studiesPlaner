package utlis

import data.Subject

fun <T> transposeLists(inputList: List<List<T>>): List<List<T>> {
    val result = mutableListOf<List<T>>()
    inputList.forEach { list -> if (list.size != inputList[0].size) throw Exception("Combination List does not have the same length") }
    for (i in inputList[0].indices) {
        val sublist = mutableListOf<T>()
        for (list in inputList) {
            sublist.add(list[i])
        }
        result.add(sublist)
    }
    return result
}

fun containsListInList(listToCheck: List<Subject>, list: List<Subject>): Boolean {
    return list.containsAll(listToCheck)
}