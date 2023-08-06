import data.Date
import data.PRIORITY
import data.Subject


class StudyPlanner(private val subjects: List<Subject>) {

    private val subjectsToDo: List<Subject> = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }
    private val subjectsOption: List<Subject> = getSubjectsByProperty(subjects) { it.priority != PRIORITY.MUST }

    fun bestPractice(): Output {

        println(subjectsToDo.size)
        println(subjectsOption.size)

        findValidSearchCombinations(subjectsToDo)

        return Output("Test", "Test", "Test")
    }


    fun findValidSearchCombinations(subjects: List<Subject>): List<List<String>> {
        val validCombinations: MutableList<List<String>> = mutableListOf()
        val numberOfVariations = calculateNumberOfVariations(subjects)
        println(numberOfVariations)
        val variationMap = buildVariationDataMap(subjects)
        val sortedVariationMap = variationMap.toList().sortedByDescending { (_, value) -> value }.toMap()
        println(sortedVariationMap)
        val filteredVariationMap = sortedVariationMap.filter { (_, value) -> value > 1 }
        println(filteredVariationMap)
        val stepUpList = generatePowersOfTwoDescending(filteredVariationMap.size)
        while (stepUpList.size < sortedVariationMap.size) {
            stepUpList.add(0)
        }
        println(stepUpList)

        var index = 0
        sortedVariationMap.forEach { entry ->
            validCombinations.add(
                generateCombinationList(
                    entry.key,
                    stepUpList[index],
                    entry.value,
                    numberOfVariations
                )
            )
            index++
        }

        println(transposeLists(validCombinations))
        return transposeLists(validCombinations)
    }

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

    fun generateCombinationList(base: String, countUpStep: Int, maxCounter: Int, listLength: Int): List<String> {
        val combinationList = mutableListOf<String>()
        var counter = 1
        var step = 1
        for (i in 0 until listLength) {
            if (counter > step * countUpStep) step++
            if (step > maxCounter) step = 1
            combinationList.add("${base}${step}")
            counter++
        }
        println("countStepUp: ${countUpStep} maxCounter: ${maxCounter} List: ${combinationList}")
        return combinationList
    }

    private fun generatePowersOfTwoDescending(length: Int): MutableList<Int> {
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

    private fun calculateNumberOfVariations(subjects: List<Subject>): Int {
        val subjectNames = getAllSubjectNames(subjects).distinct()
        val subjectCounts = subjectNames.map { name -> getHowManyClassesHasSubject(subjects, name) }
        return subjectCounts.reduce { store, next -> store * next }
    }


    fun groupSubjectListAsList(subjects: List<Subject>): List<List<Subject>> {
        val subjectNames = getAllSubjectNames(subjects).distinct();
        println(subjectNames)
        return subjectNames.map { subjectName ->
            getSubjectsByProperty(subjects) { it.subject == subjectName }
        }

    }

    fun groupSubjectListAsMap(subjects: List<Subject>): Map<String, List<Subject>> {
        val map = mutableMapOf<String, List<Subject>>();
        val subjectNames = getAllSubjectNames(subjects).distinct();
        subjectNames.forEach { subjectName ->
            map[subjectName] = getSubjectsByProperty(subjects) { it.subject == subjectName }
        }
        return map
    }

    fun hasListAProperty(subjects: List<Subject>, property: String, condition: (Subject, String) -> Boolean): Boolean {
        return subjects.fold(false) { store, next ->
            if (store) return true
            condition(next, property)
        }
    }

    private fun getHowManyClassesHasSubject(subjects: List<Subject>, subjectName: String) =
        (getSubjectsByProperty(subjects) { it.subject == subjectName }).size

    private fun getSubjectsByProperty(subjects: List<Subject>, predicate: (Subject) -> Boolean): List<Subject> =
        subjects.filter(predicate)

    private fun getAllSubjectNames(subjects: List<Subject>) = subjects.map { it.subject }
    private fun getAllSubjectClasses(subjects: List<Subject>) = subjects.map { it.className }

    private fun printSubject(subject: Subject) =
        print("{ ${subject.subject} - ${subject.className} - ${datesToString(subject.dates)} } ")

    private fun datesToString(dates: List<Date>) = (dates.map { "${it.weekDay} - ${it.from} - ${it.to}" })

}


data class Output(
    val subject: String,
    val day: String,
    val time: String,
)