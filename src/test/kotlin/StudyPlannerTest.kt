import data.Subject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StudyPlannerTest {

    private val subject = testSubjects

    @Test
    fun bestPractice() {
        val result = StudyPlanner().getStudyPlanVariationForMust(subject)
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