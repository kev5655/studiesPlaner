import data.groupedSubjectNotValid
import data.groupedSubjectValid
import data.notValidTestSubjects
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import utlis.printSubjectPlan

class VariationKtTest {

    @Test
    fun getStudyPlanVariationForOptional() {
    }

    @Test
    fun validate() {
        val result = validate(notValidTestSubjects)

    }

    @Test
    fun getStudyPlanVariationForMust() {
    }

    @Test
    fun validateTimeRangeCombinationsAndUpdate() {
    }

    @Test
    fun validateTimeRangeCombinationsAndRemoveNotValid() {
        val notValidSubject = groupedSubjectNotValid

        val result1 = validateTimeRangeCombinationsAndRemove(notValidSubject)
        printSubjectPlan("Test - validateCombinationsAndRemove - not valid", result1)
        assertEquals(0, result1.size)
    }

    @Test
    fun validateTimeRangeCombinationsAndRemoveValid() {
        val validSubjects = groupedSubjectValid
        val result = validateTimeRangeCombinationsAndRemove(validSubjects)
        printSubjectPlan("Test - validateCombinationsAndRemove - valid", result)
        assertEquals(1, result.size)
        assertEquals(2, result[0].subjects.size)
        assertEquals(3, result[0].timeRanges.size)
    }

    @Test
    fun convert() {
    }

    @Test
    fun removeCutouts() {
    }
}