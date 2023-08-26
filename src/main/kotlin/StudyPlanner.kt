import data.PRIORITY
import data.Subject
import data.getSubjectBySubjectAndClass
import data.getSubjectsByProperty
import utlis.printReplaceTemplate
import utlis.printSubjectPlan
import utlis.printTemplate


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
        val studyPlanVariation = replaceTemplateWithSubject(subjectsToDo, subjectTemplate, determiner)
        printReplaceTemplate(studyPlanVariation)
        val validStudyPlanVariationAll =
            validateCombinationsAndUpdate(studyPlanVariation).distinctBy { it.subjects }
        printSubjectPlan("validStudyPlanVariationAll", validStudyPlanVariationAll)
        val validStudyPlanVariation = removeCutouts(validStudyPlanVariationAll)

        printSubjectPlan("validStudyPlanVariation", validStudyPlanVariation)



        return Output("Test", "Test", "Test")
    }


    fun removeCutouts(validStudyPlan: List<SubjectsWidthTimeRange>): List<SubjectsWidthTimeRange> {

        val sorted = validStudyPlan.sortedBy { it.subjects.size }.toMutableList()
        printSubjectPlan("Sorted List", sorted)
        for (i in 0 until sorted.size) {
            for (j in i + 1 until sorted.size - 1) {
                val fistItem = sorted[i].subjects
                val secondItem = sorted[j].subjects
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

    fun validateCombinationsAndUpdate(studyPlans: List<List<Subject>>): List<SubjectsWidthTimeRange> {
        val validStudyPlan: MutableList<MutableList<SubjectsWidthTimeRange>> = mutableListOf()
        studyPlans.forEachIndexed { index, studyPlan ->
            validStudyPlan.add(mutableListOf())
            studyPlan.indices.forEach { i ->
                val firstItem = studyPlan[i]
                validStudyPlan[index].add(
                    i, SubjectWithTimeRangeFactory(firstItem)
                )
                for (j in i + 1 until studyPlan.size) {
                    val secondItem = studyPlan[j]

                    if (!rangeListOverlap(
                            validStudyPlan[index][i].timeRanges,
                            TimeRange.getTimeRangesFromDates(secondItem.dates)
                        )
                    ) {
                        validStudyPlan[index][i].subjects.add(secondItem)
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

    fun validateCombinationsAndRemove(studyPlans: List<List<Subject>>): List<SubjectsWidthTimeRange> {
        val validStudyPlan: MutableList<SubjectsWidthTimeRange> = mutableListOf()

        for (studyPlan in studyPlans) {

            //validStudyPlan.add(SubjectWithTimeRangeFactory(studyPlan.last()))
            subject@ for (i in studyPlan.indices) {
                val subject = studyPlan[i]
                val validTimeRanges = validStudyPlan.getOrNull(i - 1)?.timeRanges
                if (validTimeRanges != null) {
                    validStudyPlan.add(i, SubjectWithTimeRangeFactory(subject))
                    continue@subject
                }
                for (j in i + 1 until studyPlan.size) {
                    val eachSubjectTimeRanges = TimeRange.getTimeRangesFromDates(studyPlan[j].dates)
                    if (rangeListOverlap(validTimeRanges!!, eachSubjectTimeRanges)) {
                        validStudyPlan.removeAt(i)
                        continue@subject
                    }


                }

                validStudyPlan.add(i, SubjectWithTimeRangeFactory(subject))

            }
        }
        return validStudyPlan

    }

    private fun rangeListOverlap(rangeList1: List<TimeRange>, rangeList2: List<TimeRange>): Boolean {
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
                val subAndClass = it.split(delimiter)
                getSubjectBySubjectAndClass(subjects, subAndClass[0], subAndClass[1])
            }
        }

        return list
    }


    // ToDo make Generic not with Subject and move to ListUtlis
    fun hasListAProperty(subjects: List<Subject>, property: String, condition: (Subject, String) -> Boolean): Boolean {
        return subjects.fold(false) { store, next ->
            if (store) return true
            condition(next, property)
        }
    }


}


data class SubjectsWidthTimeRange(
    val subjects: MutableList<Subject>,
    val timeRanges: MutableList<TimeRange>,
)

private fun SubjectWithTimeRangeFactory(subject: Subject): SubjectsWidthTimeRange {
    return SubjectsWidthTimeRange(
        mutableListOf(subject),
        subject.dates.map { TimeRange(it.weekDay, it.from, it.to) }.toMutableList()
    )
}

data class Output(
    val subject: String,
    val day: String,
    val time: String,
)
