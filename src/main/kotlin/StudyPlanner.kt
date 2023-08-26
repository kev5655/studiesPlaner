import data.Subject
import data.getSubjectBySubjectAndClass
import utlis.printReplaceTemplate
import utlis.printSubjectPlan
import utlis.printTemplate


class StudyPlanner() {

    //private var subjectsOption: List<Subject>

    init {
        //subjectsOption = getSubjectsByProperty(subjects) { it.priority != PRIORITY.MUST }
    }

    fun getStudyPlanVariationForOptional(subjects: List<Subject>): Output {
//        val subjectsToDo = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }

        println("Must Subject length: ${subjects.size}")
        val determiner = "-"

        val subjectTemplate = findCombinationTemplate(subjects, determiner)
        printTemplate("Template for StudyPlan", subjectTemplate)
        val studyPlanVariation = replaceTemplateWithSubject(subjects, subjectTemplate, determiner)
        printReplaceTemplate("Replaced StudyPlan", studyPlanVariation)
        val validStudyPlanVariationAll =
            validateCombinationsAndUpdate(studyPlanVariation).distinctBy { it.subjects }
        printSubjectPlan("validStudyPlanVariationAll", validStudyPlanVariationAll)
        val validStudyPlanVariation = removeCutouts(validStudyPlanVariationAll)

        printSubjectPlan("validStudyPlanVariation", validStudyPlanVariation)



        return Output("Test", "Test", "Test")
    }

    fun getStudyPlanVariationForMust(subjects: List<Subject>): Output {
//        val subjectsToDo = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }

        println("Must Subject length: ${subjects.size}")
        val determiner = "-"

        val subjectTemplate = findCombinationTemplate(subjects, determiner)
        printTemplate("Template for StudyPlan", subjectTemplate)
        val studyPlanVariation = replaceTemplateWithSubject(subjects, subjectTemplate, determiner)
        printReplaceTemplate("Replaced StudyPlan", studyPlanVariation)
        val validStudyPlanVariationAll =
            validateCombinationsAndRemove(studyPlanVariation).distinctBy { it.subjects }
        printSubjectPlan("validStudyPlanVariationAll", validStudyPlanVariationAll)
        val validStudyPlanVariation = removeCutouts(validStudyPlanVariationAll)

        printSubjectPlan("validStudyPlanVariation", validStudyPlanVariation)



        return Output("Test", "Test", "Test")
    }


    fun removeCutouts(validStudyPlan: List<StudyPlan>): List<StudyPlan> {

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

    fun validateCombinationsAndUpdate(studyPlans: List<List<Subject>>): List<StudyPlan> {
        val validStudyPlan: MutableList<MutableList<StudyPlan>> = mutableListOf()
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

    fun validateCombinationsAndRemove(studyPlans: List<List<Subject>>): List<StudyPlan> {

        printReplaceTemplate("StudyPlan before converted", studyPlans)
        val convStudyPlan = convert(studyPlans).toMutableList()
        printSubjectPlan("conv StudyPlan", convStudyPlan)

        convStudyPlan.removeIf { rangeListOverlap(it.timeRanges) }

        return convStudyPlan
    }

    private fun convert(studyPlans: List<List<Subject>>): List<StudyPlan> {
        val converted = mutableListOf<StudyPlan>()

        for ((index, studyPlan) in studyPlans.withIndex()) {
            for (subject in studyPlan) {
                if (converted.getOrNull(index) == null) {
                    converted.add(
                        StudyPlan(
                            mutableListOf(subject),
                            TimeRange.getTimeRangesFromDates(subject.dates).toMutableList()
                        )
                    )
                } else {
                    converted[index].subjects.add(subject)
                    converted[index].timeRanges.addAll(TimeRange.getTimeRangesFromDates(subject.dates))
                }
            }
        }
        return converted
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


data class StudyPlan(
    val subjects: MutableList<Subject>,
    val timeRanges: MutableList<TimeRange>,
)

private fun SubjectWithTimeRangeFactory(subject: Subject): StudyPlan {
    return StudyPlan(
        mutableListOf(subject),
        subject.dates.map { TimeRange(it.weekDay, it.from, it.to) }.toMutableList()
    )
}

data class Output(
    val subject: String,
    val day: String,
    val time: String,
)
