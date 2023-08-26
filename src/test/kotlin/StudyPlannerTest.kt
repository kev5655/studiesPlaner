import data.Subject
import data.groupedSubjectNotValid
import data.groupedSubjectValid
import data.testSubjects
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utlis.printSubjectPlan

class StudyPlannerTest {

    private val subject = testSubjects

    @Test
    fun bestPractice() {
        val result = StudyPlanner().getStudyPlanVariationForOptional(subject)
    }

    @Test
    fun validateCombinationsAndUpdate() {
        val subjects = listOf(listOf(subject[0]))

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

        val resultFoundId: Boolean = StudyPlanner().hasListAProperty(subject, "TestIdA1", condHasId)
        val resultNotFoundId: Boolean = StudyPlanner().hasListAProperty(subject, "TestIdX1", condHasId)
        val resultFoundClass: Boolean = StudyPlanner().hasListAProperty(subject, "1", condHasClass)
        val resultNotFoundClass: Boolean = StudyPlanner().hasListAProperty(subject, "99", condHasClass)

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