import data.*


class StudyPlanner() {

    //private var subjectsOption: List<Subject>

    init {
        //subjectsOption = getSubjectsByProperty(subjects) { it.priority != PRIORITY.MUST }
    }

    fun getStudyPlanVariationForMust(subjects: List<Subject>): Output {
        val subjectsToDo = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }

        println("Must Subject length: ${subjectsToDo.size}")
        val determiner = "-"

        val subjectTemplate = findCombinationTemplate(subjectsToDo, determiner)
        printTemplate(subjectTemplate)
        val studyPlanVariation = replaceTemplateWithSubject(subjectsToDo, subjectTemplate, determiner);
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
                            TimeRange.getTimeRangesFromDates(secondItem.dates)
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

    // ToDo Fix Lambda func
    private fun replaceTemplateWithSubject(
        subjects: List<Subject>,
        searchList: List<List<String>>,
        delimiter: String
    ): List<List<Subject>> {
        val list = searchList.map {
            it.map {
                val subAndClass = it.split(delimiter);
                getSubjectBySubjectAndClass(subjects, subAndClass[0], subAndClass[1])
            }
        }

        return list;
    }


    // ToDo make Generic not with Subject and move to ListUtlis
    fun hasListAProperty(subjects: List<Subject>, property: String, condition: (Subject, String) -> Boolean): Boolean {
        return subjects.fold(false) { store, next ->
            if (store) return true
            condition(next, property)
        }
    }


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
