import data.Subject
import data.getSubjectBySubjectAndClass
import utlis.containsListInList
import utlis.printReplaceTemplate
import utlis.printSubjectPlan
import utlis.printTemplate


class StudyPlanner() {


    // ToDo Not Working fix it
    fun findStudyVariation(mustStudyPlans: List<StudyPlan>, optionalStudyPlans: List<Subject>): List<StudyPlan> {
        // ToDo iterate all must and check is optionalSubject in must and time range not overlap when not add to must or a new list

        val optionVariation = findAllVariation(optionalStudyPlans)
        printReplaceTemplate("Optional Variation", optionVariation)
        println("Size befor validateDoubleSubjectsAndUpdate ${optionVariation.size}")

//        val valid = validateDoubleSubjectsAndUpdate(optionVariation)
//        printReplaceTemplate("validateDoubleSubjectsAndUpdate", valid)
//        println("Size after validateDoubleSubjectsAndUpdate ${valid.size}")
        val validOptionVariation = validateTimeRangeCombinationsAndUpdate(optionVariation)
        println("Size after validateTimeRangeCombinationsAndUpdate ${validOptionVariation.size}")

//        printSubjectPlan("Valid Variations", validOptionVariation)


        val variation = mutableListOf<StudyPlan>()
        for (must in mustStudyPlans) {
            for (optional in validOptionVariation) {
                if (!rangeListOverlap(must.timeRanges, optional.timeRanges)) {
                    val studyPlan = (must.subjects + optional.subjects).toMutableList()
                    val timeRanges = (must.timeRanges + optional.timeRanges).toMutableList()
                    variation.add(StudyPlan(studyPlan, timeRanges))
                }
            }
        }

        return variation.sortedBy { it.subjects.size }
    }

    fun findAllVariation(subjects: List<Subject>): List<List<Subject>> {
        val variation = mutableListOf<List<Subject>>()
        for (i in 1..subjects.size) {
            variation.addAll(subjects.combinations(i))
        }
        printReplaceTemplate("All Variaton", variation)
        return variation
    }

    private fun <T> List<T>.combinations(length: Int): List<List<T>> {
        if (length == 1) {
            return map { listOf(it) }
        }
        if (isEmpty() || length > size) {
            return emptyList()
        }

        val combinations = mutableListOf<List<T>>()

        val first = subList(0, 1)
        val subCombinations = subList(1, size).combinations(length - 1)

        for (subCombination in subCombinations) {
            val newCombination = mutableListOf<T>()
            newCombination.addAll(first)
            newCombination.addAll(subCombination)
            combinations.add(newCombination)
        }

        combinations.addAll(subList(1, size).combinations(length))

        return combinations
    }

    fun validateDoubleSubjectsAndUpdate(list: List<List<Subject>>): List<List<Subject>> {
        val valid = mutableListOf<List<Subject>>()
        val sameElements = mutableListOf<List<Subject>>()

        for (studyPlan in list) {
            sameElements.clear()
            for (i in studyPlan.indices) {
                for (j in i until studyPlan.size) {
                    val subject = studyPlan[i]
                    val eachSubject = studyPlan[j]

                    if (subject.subject == eachSubject.subject) {
                        sameElements.add(listOf(subject, eachSubject))
                    }
                }
            }
            if (sameElements.isNotEmpty()) {
                for (temp in sameElements) {
                    for (subjectToRemove in temp) {
                        val copy = studyPlan.toMutableList()
                        copy.remove(subjectToRemove)
                        valid.add(copy)
                    }
                }
            } else {
                valid.add(studyPlan)
            }
        }

        return valid
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


data class Output(
    val subject: String,
    val day: String,
    val time: String,
)
