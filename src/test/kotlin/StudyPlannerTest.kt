import data.Date
import data.PRIORITY
import data.Subject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Objects

class StudyPlannerTest {
    private val testSubjects: List<Subject> =
        listOf(
            Subject(
                "Test",
                "TestIdA1",
                "A",
                PRIORITY.MUST,
                "Full Time",
                "1",
                "01.01.2023",
                "02.02.2023",
                listOf(
                    Date("Mon", "20:00", "21:00", true),
                    Date("Mon", "20:00", "21:00", true),
                )
            ),
            Subject(
                "Test",
                "TestIdA2",
                "A",
                PRIORITY.MUST,
                "Full Time",
                "2",
                "01.01.2023",
                "02.02.2023",
                listOf(
                    Date("Mon", "20:00", "21:00", true)
                )
            ),
            Subject("Test",
                "TestIdB1",
                "B",
                PRIORITY.MUST,
                "Full Time",
                "1",
                "01.01.2023",
                "02.02.2023",
                listOf(
                    Date("Mon", "20:00", "21:00",  true)
                )
            ),
            Subject("Test",
                "TestIdB2",
                "B",
                PRIORITY.MUST,
                "Full Time",
                "2",
                "01.01.2023",
                "02.02.2023",
                listOf(
                    Date("Mon", "20:00", "21:00",  true)
                )
            ),
            Subject("Test",
                "TestIdB3",
                "B",
                PRIORITY.MUST,
                "Full Time",
                "3",
                "01.01.2023",
                "02.02.2023",
                listOf(
                    Date("Mon", "20:00", "21:00",  true)
                )
            ),
            Subject("Test",
                "TestIdC1",
                "C",
                PRIORITY.MUST,
                "Full Time",
                "1",
                "01.01.2023",
                "02.02.2023",
                listOf(
                    Date("Mon", "20:00", "21:00",  true)
                )
            )
        )

    var obj: StudyPlanner? = null;

    @BeforeEach
    fun setUp() {
        obj = StudyPlanner(testSubjects);
    }

    @Test
    fun bestPractice() {
    }

    @Test
    fun getValidVariation(): Unit {
        val result = obj?.getValidVariation(this.testSubjects) ?: throw Exception("obj is null")


        println(result[0])
        assert(result[0][0].subject == "A")
        assert(result[0][0].className == "1")
        assert(result[0][1].subject == "B")
        assert(result[0][1].className == "1")
        assert(result[0][2].subject == "C")
        assert(result[0][2].className == "1")

    }

    @Test
    fun hasListAProperty(): Unit {
        val condHasId: (Subject, String) -> Boolean = { subject, property -> subject.id == property }
        val condHasClass: (Subject, String) -> Boolean = { subject, property -> subject.className == property }
        val testObject: StudyPlanner = if (Objects.nonNull(obj)) obj!! else throw Exception("Obj is null")

        val resultFoundId: Boolean = testObject.hasListAProperty(this.testSubjects, "TestIdA1", condHasId)
        val resultNotFoundId: Boolean = testObject.hasListAProperty(this.testSubjects, "TestIdX1", condHasId)
        val resultFoundClass: Boolean = testObject.hasListAProperty(this.testSubjects, "1", condHasClass)
        val resultNotFoundClass: Boolean = testObject.hasListAProperty(this.testSubjects, "99", condHasClass)

        assertTrue(resultFoundId)
        assertFalse(resultNotFoundId)
        assertTrue(resultFoundClass)
        assertFalse(resultNotFoundClass)
    }


    @Test
    fun groupSubjectListAsList() {
        val testObject: StudyPlanner = if (Objects.nonNull(obj)) obj!! else throw Exception("Obj is null")
        val result = testObject.groupSubjectListAsList(this.testSubjects)

        assertEquals(3, result.size)
        assertEquals(2, result[0].size)
        assertEquals(3, result[1].size)
        assertEquals(1, result[2].size)

    }

    @Test
    fun testGroupSubjectListAsMap() {
        val testObject: StudyPlanner = if (Objects.nonNull(obj)) obj!! else throw Exception("Obj is null")
        val result = testObject.groupSubjectListAsMap(this.testSubjects)


        assertSubjectList(result, "A", 2, listOf("TestIdA1", "TestIdA2"))
        assertSubjectList(result, "B", 3, listOf("TestIdB1", "TestIdB2", "TestIdB3"))
        assertSubjectList(result, "C", 1, listOf("TestIdC1"))
    }

    private fun assertSubjectList(
        result: Map<String, List<Subject>>,
        group: String,
        expectedSize: Int,
        expectedIds: List<String>
    ) {
        assertNotNull(result[group])
        assertEquals(expectedSize, result[group]?.size)

        val containsAllIds = result[group]?.all { it.id in expectedIds } ?: false
        assertTrue(containsAllIds)
    }
}