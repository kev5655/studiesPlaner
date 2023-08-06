import data.Date
import data.PRIORITY
import data.Subject


class StudyPlanner(private val subjects: List<Subject>) {

    private val subjectsToDo: List<Subject> = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }
    private val subjectsOption: List<Subject> = getSubjectsByProperty(subjects) { it.priority != PRIORITY.MUST }

    fun bestPractice(): Output {

        println(subjectsToDo.size)
        println(subjectsOption.size)

        getValidSearchList(subjectsToDo)


        return Output("Test", "Test", "Test")
    }


    fun getValidSearchList(subjects: List<Subject>): List<List<String>> {
        val combination: MutableList<List<String>> = mutableListOf()
        val numberOfVariation = calcNumberOfVariation(subjects)
        println(numberOfVariation)
        val variationMap = variationDataStruct(subjects)
        val sortedMap = variationMap.toList().sortedByDescending { (_, value) -> value }.toMap()
        println(variationMap)
        val filteredMap = sortedMap.filter { (_, value) -> value > 1 }
        println(filteredMap)
        val stepUpList = generatePowersOfTwoDescending(filteredMap.size)
        while (stepUpList.size < sortedMap.size) {
            stepUpList.add(0)
        }
        println(stepUpList)

        var index = 0
        sortedMap.forEach { entry ->
            combination.add(generateList(entry.key, stepUpList[index], entry.value, numberOfVariation))
            index++
        }


        println(transposeLists(combination))
        return transposeLists(combination);
    }

    fun <T> transposeLists(inputList: List<List<T>>): List<List<T>> {
        val result = mutableListOf<List<T>>()
        inputList.forEach { list -> if (list.size != inputList[0].size) throw Exception("Combination List has not the same length") }
        for (i in inputList[0].indices) {
            val sublist = mutableListOf<T>()
            for (list in inputList) {
                sublist.add(list[i])
            }
            result.add(sublist)
        }
        return result
    }

    fun generateList(base: String, countUpStep: Int, maxCounter: Int, listLength: Int): List<String> {
        val list = mutableListOf<String>()
        var counter = 1
        var step = 1
        for (i in 0 until listLength) {
            if (counter > step * countUpStep) step++
            if (step > maxCounter) step = 1;
            list.add("${base}${step}")
            counter++
        }
        println("countStepUp: ${countUpStep} maxCounter: ${maxCounter} List: ${list}")
        return list;
    }

    fun generatePowersOfTwoDescending(length: Int): MutableList<Int> {
        val powersOfTwo = mutableListOf<Int>()

        var value = 1
        var count = 0

        while (count < length) {
            powersOfTwo.add(value)
            value *= 2
            count++
        }
        return powersOfTwo.reversed().toMutableList()
    }


    fun variationDataStruct(subjects: List<Subject>): Map<String, Int> {
        val names = getAllSubjectNames(subjects).distinct()
        val numbers = names.map { name -> getHowManyClassesHasSubject(subjects, name) }
        if (names.size != numbers.size) throw Exception("List of names and List of numbers are have not the same length")

        val map = mutableMapOf<String, Int>()

        for ((index, name) in names.withIndex()) {
            map[name] = numbers[index]
        }

        return map;
    }

    fun calcNumberOfVariation(subjects: List<Subject>): Int {
        val names = getAllSubjectNames(subjects).distinct()
        val numbers = names.map { name -> getHowManyClassesHasSubject(subjects, name) }
        return numbers.reduce() { store, next -> store * next }
    }

    fun groupSubjectListAsList(subject: List<Subject>): List<List<Subject>> {
        val subjectNames = getAllSubjectNames(subjects).distinct();
        println(subjectNames)
        return subjectNames.map { subjectName ->
            getSubjectsByProperty(subjects) { it.subject == subjectName }
        }

    }

    fun groupSubjectListAsMap(subject: List<Subject>): Map<String, List<Subject>> {
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

    private fun datesToString(dates: List<Date>) = (dates.map { it -> "${it.weekDay} - ${it.from} - ${it.to}" })

}


data class Output(
    val subject: String,
    val day: String,
    val time: String,
)

//val names = getAllSubjectNames(subjects).distinct();
//        val classes = getAllSubjectClasses(subjects).distinct();
//        val lenght = if (names.size > classes.size) names.size.toDouble()
//            .pow(classes.size.toDouble()) else classes.size.toDouble()
//            .pow(names.size.toDouble())
//
//        val maxFistIt = (lenght / names.size).toInt()
//
//        for (index in 0..maxFistIt) {
//            val name = names[index]
//
//        }
//
//        val resultArray = mutableListOf<List<String>>()