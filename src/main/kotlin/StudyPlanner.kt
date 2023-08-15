import data.Date
import data.PRIORITY
import data.Subject
import utlis.Math


class StudyPlanner() {

    //private var subjectsOption: List<Subject>

    init {
        //subjectsOption = getSubjectsByProperty(subjects) { it.priority != PRIORITY.MUST }
    }

    fun getStudyPlanVariationForMust(subjects: List<Subject>): Output {
        val subjectsToDo = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }

        println("Must Subject length: ${subjectsToDo.size}")

        val subjectTemplate = findCombinationTemplate(subjectsToDo)
        printTemplate(subjectTemplate)
        val studyPlanVariation = replaceTemplateWithSubject(subjectsToDo, subjectTemplate);
        printReplaceTemplate(studyPlanVariation)
        val validStudyPlanVariationAll =
            validateCombinationsAndUpdate(studyPlanVariation).distinctBy { it.subject }
        printSubjectPlan("validStudyPlanVariationAll", validStudyPlanVariationAll)
        val validStudyPlanVariation = removeCutouts(validStudyPlanVariationAll);

        printSubjectPlan("validStudyPlanVariation", validStudyPlanVariation)



        return Output("Test", "Test", "Test")
    }


    fun removeCutouts(validStudyPlan: List<SubjectWidthTimeRange>): List<SubjectWidthTimeRange> {

        val sorted = validStudyPlan.sortedBy { it.subject.size }.toMutableList()
        printSubjectPlan("Sorted List", sorted)
        for (i in 0 until sorted.size) {
            for (j in i + 1 until sorted.size - 1) {
                val fistItem = sorted[i].subject
                val secondItem = sorted[j].subject
                if (containsListInList(fistItem, secondItem)) {
                    sorted.removeAt(i)
                }
            }
        }

        printSubjectPlan("List without removed Subject that are in the list twice ", sorted)

        return validStudyPlan
    }

    private fun containsListInList(listToCheck: List<Subject>, list: List<Subject>): Boolean {
        return list.containsAll(listToCheck)
    }

    private fun validateCombinationsAndUpdate(studyPlans: List<List<Subject>>): List<SubjectWidthTimeRange> {
        val validStudyPlan: MutableList<MutableList<SubjectWidthTimeRange>> = mutableListOf();
        studyPlans.forEachIndexed { index, studyPlan ->
            validStudyPlan.add(mutableListOf())
            studyPlan.indices.forEach { i ->
                val firstItem = studyPlan[i]
                validStudyPlan[index].add(
                    i, SubjectWidthTimeRange(
                        mutableListOf(firstItem),
                        firstItem.dates.map { TimeRange(it.weekDay, it.from, it.to) }.toMutableList()
                    )
                )
                for (j in i + 1 until studyPlan.size) {
                    validStudyPlan[index][i].subject
                    val secondItem = studyPlan[j]

                    if (!doTwoTimeRangeListOverlap(
                            validStudyPlan[index][i].timeRanges,
                            getTimeRangesFromDates(secondItem.dates)
                        )
                    ) {
                        validStudyPlan[index][i].subject.add(secondItem)
                        validStudyPlan[index][i].timeRanges.addAll(secondItem.dates.map {
                            TimeRange(
                                it.weekDay,
                                it.from,
                                it.to
                            )
                        })
                    }
                }
            }
        }

        return validStudyPlan.flatten()
    }

    private fun getTimeRangesFromDates(list: List<Date>): List<TimeRange> =
        list.map { TimeRange(it.weekDay, it.from, it.to) }

    private fun doTwoTimeRangeListOverlap(rangeList1: List<TimeRange>, rangeList2: List<TimeRange>): Boolean {
        for (range1 in rangeList1) {
            for (range2 in rangeList2) {
                if (TimeRange.doTimeRangeOverlap(range1, range2)) {
                    return true
                }
            }
        }
        return false
    }

    private fun replaceTemplateWithSubject(
        subjects: List<Subject>,
        searchList: List<List<String>>
    ): List<List<Subject>> {
        val list = searchList.map {
            it.map {
                val subAndClass = it.split("-");
                getSubjectBySubjectAndClass(subjects, subAndClass[0], subAndClass[1])
            }
        }

        return list;
    }


    //ToDo Refactoring maybe make not a Map in buildVariationMap
    fun findCombinationTemplate(subjects: List<Subject>): List<List<String>> {
        val validCombinations: MutableList<List<String>> = mutableListOf()
        val numberOfVariations = calculateNumberOfVariations(subjects)
        val variationMap = buildVariationDataMap(subjects)
        val sortedVariationMap = variationMap.toList().sortedByDescending { (_, value) -> value }.toMap()
        val filteredVariationMap = sortedVariationMap.filter { (_, value) -> value > 1 }
        val stepUpList = Math().generatePowersOfTwoDescending(filteredVariationMap.size).toMutableList()
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

    private fun <T> transposeLists(inputList: List<List<T>>): List<List<T>> {
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
            combinationList.add("${base}-${variationMap.get(step)}")
            counter++
        }
        return combinationList
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
        val subjectNames = getAllSubjectNames(subjects).distinct()
        println("Subject names: $subjectNames")
        return subjectNames.map { subjectName ->
            getSubjectsByProperty(subjects) { it.subject == subjectName }
        }

    }

    fun groupSubjectListAsMap(subjects: List<Subject>): Map<String, List<Subject>> {
        val map = mutableMapOf<String, List<Subject>>()
        val subjectNames = getAllSubjectNames(subjects).distinct()
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

    private fun getSubjectBySubjectAndClass(subjects: List<Subject>, subjectName: String, _class: String) =
        subjects
            .filter { it.subject == subjectName }
            .find { it.className == _class }
            ?: throw Exception("subject not found subjectName: ${subjectName}, class: ${_class}")

    private fun getSubjectsByProperty(subjects: List<Subject>, predicate: (Subject) -> Boolean): List<Subject> =
        subjects.filter(predicate)

    private fun getAllSubjectNames(subjects: List<Subject>) = subjects.map { it.subject }
    private fun getAllSubjectClasses(subjects: List<Subject>) = subjects.map { it.className }

    private fun printSubjectPlan(name: String, list: List<SubjectWidthTimeRange>) {
        println("---------------------------------")
        println("$name - List Size: " + list.size)
        for (item in list) {
            item.subject.forEach { printSubject(it) }
            println()
        }
        println("---------------------------------")
    }

    private fun printTemplate(template: List<List<String>>) {
        println("---------------------------------")
        println("Subject Template")
        for (list in template) {
            for (item in list) {
                print("[${item}] ")
            }
            println()
        }
        println("---------------------------------")
    }


    private fun printReplaceTemplate(studyPlanVariation: List<List<Subject>>) {
        println("---------------------------------")
        println("Replaced Template with Subjects")
        for (list in studyPlanVariation) {
            for (item in list) {
                printSubject(item)
            }
            println()
        }
        println("---------------------------------")

    }

    private fun printSubject(subject: Subject) =
        print("[{ ${subject.subject} - ${subject.className} - ${datesToString(subject.dates)} }] ")

    private fun datesToString(dates: List<Date>) = (dates.map { "${it.weekDay} - ${it.from} - ${it.to}" })

}


data class SubjectWidthTimeRange(
    val subject: MutableList<Subject>,
    val timeRanges: MutableList<TimeRange>,
)

data class Output(
    val subject: String,
    val day: String,
    val time: String,
)
