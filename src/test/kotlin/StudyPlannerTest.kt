import data.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utlis.printSubjectPlan

class StudyPlannerTest {

    private val notValidsubject = notValidTestSubjects

//    @Test
//    fun findStudyVariation() {
//
//        val must1 = validStudyPLanMustAndOptional[0]
//        val optional1 = validStudyPLanMustAndOptional[1]
//
//        val result1 = StudyPlanner().findStudyVariation(must1, optional1)
//        printSubjectPlan("Test - Result", result1)
//        assertEquals(4, result1.size)
//        assertEquals(3, result1[0].subjects.size)
//        assertEquals(3, result1[1].subjects.size)
//        assertEquals(3, result1[2].subjects.size)
//        assertEquals(3, result1[3].subjects.size)
//
//        val must2 = notValidStudyPLanMustAndOptional[0]
//        val optional2 = notValidStudyPLanMustAndOptional[1]
//        val result2 = StudyPlanner().findStudyVariation(must2, optional2)
//        printSubjectPlan("Test - Result 2", result2)
//        assertEquals(3, result2.size)
//        assertEquals(3, result2[0].subjects.size)
//        assertEquals(3, result2[1].subjects.size)
//        assertEquals(3, result2[2].subjects.size)
//    }

    @Test
    fun validateDoubleSubjectsAndUpdate() {
        val subject = listOf(
            listOf(
                Subject(
                    "Math",
                    "id_A1_class1",
                    "A1",
                    PRIORITY.MUST,
                    "Full Time",
                    "class1",
                    "01.01.2023",
                    "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "09:00", true)
                    )
                ),
                Subject(
                    "Math",
                    "id_A1_class2",
                    "A1",
                    PRIORITY.MUST,
                    "Full Time",
                    "class2",
                    "01.01.2023",
                    "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "09:00", true)
                    )
                ),
                Subject(
                    "C++",
                    "id_A1_class1",
                    "B1",
                    PRIORITY.MUST,
                    "Full Time",
                    "class1",
                    "01.01.2023",
                    "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "09:00", true)
                    )
                ),
                Subject(
                    "Algo",
                    "id_C1_class1",
                    "C1",
                    PRIORITY.MUST,
                    "Full Time",
                    "class1",
                    "01.01.2023",
                    "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "09:00", true)
                    )
                ),
                Subject(
                    "Algo",
                    "id_C1_class2",
                    "C1",
                    PRIORITY.MUST,
                    "Full Time",
                    "class2",
                    "01.01.2023",
                    "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "09:00", true)
                    )
                ),
            )
        )


        val result = StudyPlanner().validateDoubleSubjectsAndUpdate(subject)
        assertEquals(3, result[0].size)
        assertEquals(4, result.size)
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