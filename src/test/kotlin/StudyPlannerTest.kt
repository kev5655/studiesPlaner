import data.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utlis.printSubjectPlan

class StudyPlannerTest {

    private val notValidsubject = notValidTestSubjects

    @Test
    fun bestPractice() {
        val result = StudyPlanner().getStudyPlanVariationForOptional(notValidsubject)
    }

    @Test
    fun getStudyPlanVariationForOptional() {

        val result = StudyPlanner().getStudyPlanVariationForOptional(notValidsubject)
        printSubjectPlan("Test - Result", result)


    }

    @Test
    fun findStudyVariation() {

        val must1 = validStudyPLanMustAndOptional[0]
        val optional1 = validStudyPLanMustAndOptional[1]

        val result1 = StudyPlanner().findStudyVariation(must1, optional1)
        printSubjectPlan("Test - Result", result1)
        assertEquals(4, result1.size)
        assertEquals(3, result1[0].subjects.size)
        assertEquals(3, result1[1].subjects.size)
        assertEquals(3, result1[2].subjects.size)
        assertEquals(3, result1[3].subjects.size)

        val must2 = notValidStudyPLanMustAndOptional[0]
        val optional2 = notValidStudyPLanMustAndOptional[1]
        val result2 = StudyPlanner().findStudyVariation(must2, optional2)
        printSubjectPlan("Test - Result 2", result2)
        assertEquals(3, result2.size)
        assertEquals(3, result2[0].subjects.size)
        assertEquals(3, result2[1].subjects.size)
        assertEquals(3, result2[2].subjects.size)
    }

    @Test
    fun validateCombinationsAndUpdate() {
        val subjects = listOf(listOf(notValidsubject[0]))

        val result = StudyPlanner().validateCombinationsAndUpdate(subjects)
    }

    @Test
    fun validateCombinationsAndRemoveNotValid() {
        val notValidSubject = groupedSubjectNotValid

        val result1 = StudyPlanner().validateCombinationsAndRemove(notValidSubject)
        printSubjectPlan("Test - validateCombinationsAndRemove - not valid", result1)
        assertEquals(0, result1.size)

    }

    @Test
    fun validateCombinationAndRemoveValid() {
        val validSubjects = groupedSubjectValid
        val result = StudyPlanner().validateCombinationsAndRemove(validSubjects)
        printSubjectPlan("Test - validateCombinationsAndRemove - valid", result)
        assertEquals(1, result.size)
        assertEquals(2, result[0].subjects.size)
        assertEquals(3, result[0].timeRanges.size)

    }


    @Test
    fun getValidVariation() {

    }

    @Test
    fun hasListAProperty() {
        val condHasId: (Subject, String) -> Boolean = { subject, property -> subject.id == property }
        val condHasClass: (Subject, String) -> Boolean = { subject, property -> subject.className == property }

        val resultFoundId: Boolean = StudyPlanner().hasListAProperty(notValidsubject, "TestIdA1", condHasId)
        val resultNotFoundId: Boolean = StudyPlanner().hasListAProperty(notValidsubject, "TestIdX1", condHasId)
        val resultFoundClass: Boolean = StudyPlanner().hasListAProperty(notValidsubject, "1", condHasClass)
        val resultNotFoundClass: Boolean = StudyPlanner().hasListAProperty(notValidsubject, "99", condHasClass)

        assertTrue(resultFoundId)
        assertFalse(resultNotFoundId)
        assertTrue(resultFoundClass)
        assertFalse(resultNotFoundClass)
    }


    private fun assertSubjectList(
        result: Map<String, List<Subject>>, group: String, expectedSize: Int, expectedIds: List<String>
    ) {
        assertNotNull(result[group])
        assertEquals(expectedSize, result[group]?.size)

        val containsAllIds = result[group]?.all { it.id in expectedIds } ?: false
        assertTrue(containsAllIds)
    }
}