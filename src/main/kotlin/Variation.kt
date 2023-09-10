import data.Subject
import data.getSubjectBySubjectAndClass
import utlis.containsListInList
import utlis.printReplaceTemplate
import utlis.printSubjectPlan
import utlis.printTemplate


// ToDo Not Working
fun getStudyPlanVariationForOptional(subjects: List<Subject>): List<StudyPlan> {
    println("Must Subject length: ${subjects.size}")

    val studyPlanVariation = templateVariation(subjects);

    printReplaceTemplate("Replaced StudyPlan", studyPlanVariation)
    val validStudyPlanVariationAll =
        validateTimeRangeCombinationsAndUpdate(studyPlanVariation).distinctBy { it.subjects }
    printSubjectPlan("validStudyPlanVariationAll", validStudyPlanVariationAll)
//        val validStudyPlanVariation = removeCutouts(validStudyPlanVariationAll)

//        printSubjectPlan("validStudyPlanVariation", validStudyPlanVariation)


    return validStudyPlanVariationAll
}

fun validate(studyPlans: List<Subject>): List<StudyPlan> {
    val studyPlanVariation = convert(templateVariation(studyPlans));
    printSubjectPlan("Template before validation", studyPlanVariation)

    val valid = validateUpdate(studyPlanVariation)
//    printSubjectPlan("Template after validation", valid)

    return valid;
}

fun validateUpdate(studyPlans: List<StudyPlan>): List<StudyPlan> {
    val copy = studyPlans.toMutableList()
    val newElements = mutableListOf<List<StudyPlan>>()

    val isValidGroup = copy.groupBy { !rangeListOverlap((it.timeRanges)) }
//    isValidGroup[true]?.let { printSubjectPlan("Split Group [0]", it) }
    isValidGroup[false]?.let { printSubjectPlan("Split Group [1]", it) }
//    isValidGroup[false]?.let { it.forEach() {it -> {}} }
    if (isValidGroup[false] != null) {
        val notValid: List<StudyPlan> = isValidGroup[false]!!
        val overLapSubjectList = mutableListOf(mutableListOf(mutableListOf<Subject>()))
        for ((r, studyPlan) in notValid.withIndex()) {
            overLapSubjectList.add(r, mutableListOf())
            for (i in studyPlan.timeRanges.indices) {
                val toCheck = studyPlan.timeRanges[i]
                // Only work on TimeRanges
                val studyPlanWithoutToCheck = studyPlan.timeRanges.toMutableList().filterNot { it == toCheck }
                if (rangeListOverlap(studyPlanWithoutToCheck, toCheck)) {
                    overLapSubjectList[r].add(
                        i,
                        mutableListOf(toCheck.ref)
                    ) // Add first Element when toCheck has overlaping elements in studyPlanWithoutToCheck
                    for (j in i until studyPlan.timeRanges.size) {
                        val element = studyPlan.timeRanges[j]
                        if (doTimeRangeOverlap(toCheck, element)) {
                            overLapSubjectList[r][i].add(i, element.ref)
                        }
                    }
                }
            }
        }
        println(overLapSubjectList)
    }

    return studyPlans;
}

fun getStudyPlanVariationForMust(subjects: List<Subject>): List<StudyPlan> {
    println("Must Subject length: ${subjects.size}")

    val studyPlanVariation = templateVariation(subjects);

    printReplaceTemplate("Replaced StudyPlan", studyPlanVariation)
    val validStudyPlanVariationAll =
        validateTimeRangeCombinationsAndRemove(studyPlanVariation).distinctBy { it.subjects }
    printSubjectPlan("validStudyPlanVariationAll", validStudyPlanVariationAll)
    val validStudyPlanVariation = removeCutouts(validStudyPlanVariationAll)

    printSubjectPlan("validStudyPlanVariation", validStudyPlanVariation)

    return validStudyPlanVariation
}

fun templateVariation(subjects: List<Subject>): List<List<Subject>> {
    val determiner = "#"
    val subjectTemplate = findCombinationTemplate(subjects, determiner)
    printTemplate("Template for StudyPlan", subjectTemplate)
    return replaceTemplateWithSubject(subjects, subjectTemplate, determiner)
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

fun validateTimeRangeCombinationsAndUpdate(studyPlans: List<List<Subject>>): List<StudyPlan> {
    val validStudyPlan: MutableList<MutableList<StudyPlan>> = mutableListOf()
    studyPlans.forEachIndexed { index, studyPlan ->
        validStudyPlan.add(mutableListOf())
        studyPlan.indices.forEach { i ->
            val firstItem = studyPlan[i]
            validStudyPlan[index].add(
                i, subjectWithTimeRangeFactory(firstItem)
            )
            for (j in i + 1 until studyPlan.size) {
                val secondItem = studyPlan[j]

                if (!rangeListOverlap(
                        validStudyPlan[index][i].timeRanges,
                        TimeRange.getTimeRangesFromDates(secondItem)
                    )
                ) {
                    validStudyPlan[index][i].subjects.add(secondItem)
                    validStudyPlan[index][i].timeRanges.addAll(secondItem.dates.map {
                        TimeRange(
                            it.weekDay,
                            it.from,
                            it.to,
                            secondItem
                        )
                    })
                }
            }
        }
    }

    return validStudyPlan.flatten()
}

private fun subjectWithTimeRangeFactory(subject: Subject): StudyPlan {
    return StudyPlan(
        mutableListOf(subject),
        subject.dates.map { TimeRange(it.weekDay, it.from, it.to, subject) }.toMutableList()
    )
}

fun validateTimeRangeCombinationsAndRemove(studyPlans: List<List<Subject>>): List<StudyPlan> {

    printReplaceTemplate("StudyPlan before converted", studyPlans)
    val convStudyPlan = convert(studyPlans).toMutableList()
    printSubjectPlan("conv StudyPlan", convStudyPlan)

    convStudyPlan.removeIf { rangeListOverlap(it.timeRanges) }

    return convStudyPlan
}

fun convert(studyPlans: List<List<Subject>>): List<StudyPlan> {
    val converted = mutableListOf<StudyPlan>()

    for ((index, studyPlan) in studyPlans.withIndex()) {
        for (subject in studyPlan) {
            if (converted.getOrNull(index) == null) {
                converted.add(
                    StudyPlan(
                        mutableListOf(subject),
                        TimeRange.getTimeRangesFromDates(subject).toMutableList()
                    )
                )
            } else {
                converted[index].subjects.add(subject)
                converted[index].timeRanges.addAll(TimeRange.getTimeRangesFromDates(subject))
            }
        }
    }
    return converted
}

fun removeCutouts(validStudyPlan: List<StudyPlan>): List<StudyPlan> {

    val sorted = validStudyPlan.sortedBy { it.subjects.size }.toMutableList()
    val result = mutableListOf<StudyPlan>()
    printSubjectPlan("Sorted List", sorted)
    for (i in 0 until sorted.size) {
        result.add(sorted[i])
        for (j in i + 1 until sorted.size - 1) {

            val fistItem = sorted[i].subjects
            val secondItem = sorted[j].subjects
            if (containsListInList(fistItem, secondItem)) {
                result.remove(sorted[i])
            }
        }
    }

    printSubjectPlan("List without removed Subject that are in the list twice ", sorted)

    return result
}