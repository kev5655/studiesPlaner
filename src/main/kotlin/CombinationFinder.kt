import data.Subject
import data.getAllSubjectClasses
import data.getAllSubjectNames
import data.getHowManyClassesHasSubject
import utlis.MathUtlis
import utlis.transposeLists


//ToDo Refactoring maybe make not a Map in buildVariationMap
fun findCombinationTemplate(subjects: List<Subject>): List<List<String>> {
    val validCombinations: MutableList<List<String>> = mutableListOf()
    val numberOfVariations = calculateNumberOfSubjectNamesVariations(subjects)
    val variationMap = buildVariationDataMap(subjects)
    val sortedVariationMap = variationMap.toList().sortedByDescending { (_, value) -> value }.toMap()
    val filteredVariationMap = sortedVariationMap.filter { (_, value) -> value > 1 }
    val stepUpList = MathUtlis().generatePowersOfTwoDescending(filteredVariationMap.size).toMutableList()
    while (stepUpList.size < sortedVariationMap.size) {
        stepUpList.add(0)
    }
    val classes = getAllSubjectClasses(subjects)

    var index = 0
    sortedVariationMap.forEach { entry ->
        validCombinations.add(
            generateCombinationTemplateOfOneSequence(
                entry.key,
                classes,
                stepUpList[index],
                entry.value,
                numberOfVariations
            )
        )
        index++
    }

    return transposeLists(validCombinations)
}

private fun calculateNumberOfSubjectNamesVariations(subjects: List<Subject>): Int {
    val subjectNames = getAllSubjectNames(subjects).distinct()
    val subjectCounts = subjectNames.map { name -> getHowManyClassesHasSubject(subjects, name) }
    return subjectCounts.reduce { store, next -> store * next }
}

private fun buildVariationDataMap(subjects: List<Subject>): Map<String, Int> {
    val subjectNames = getAllSubjectNames(subjects).distinct()
    val subjectCounts = subjectNames.map { name -> getHowManyClassesHasSubject(subjects, name) }
    if (subjectNames.size != subjectCounts.size) throw Exception("List of names and List of numbers do not have the same length")

    val variationDataMap = mutableMapOf<String, Int>()

    for ((index, name) in subjectNames.withIndex()) {
        variationDataMap[name] = subjectCounts[index]
    }

    return variationDataMap
}

fun generateCombinationTemplateOfOneSequence(
    base: String,
    variation: List<String>,
    countUpStep: Int,
    maxCounter: Int,
    listLength: Int
): List<String> {
    val combinationList = mutableListOf<String>()
    val variationMap = variation.mapIndexed { index, item -> index + 1 to item }.toMap()
    var counter = 1
    var step = 1
    for (i in 0 until listLength) {
        if (counter > step * countUpStep) step++
        if (step > maxCounter) step = 1
        combinationList.add("${base}-${variationMap[step]}")
        counter++
    }
    return combinationList
}