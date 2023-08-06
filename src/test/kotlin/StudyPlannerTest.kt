import data.Date
import data.PRIORITY
import data.Subject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Objects

class StudyPlannerTest {
    private val testSubjects: List<Subject> = listOf(
        Subject(
            "Test", "TestIdA1", "A", PRIORITY.MUST, "Full Time", "1", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "20:00", "21:00", true),
                Date("Mon", "20:00", "21:00", true),
            )
        ), Subject(
            "Test", "TestIdA2", "A", PRIORITY.MUST, "Full Time", "2", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "20:00", "21:00", true)
            )
        ), Subject(
            "Test", "TestIdB1", "B", PRIORITY.MUST, "Full Time", "1", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "20:00", "21:00", true)
            )
        ), Subject(
            "Test", "TestIdB2", "B", PRIORITY.MUST, "Full Time", "2", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "20:00", "21:00", true)
            )
        ), Subject(
            "Test", "TestIdB3", "B", PRIORITY.MUST, "Full Time", "3", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "20:00", "21:00", true)
            )
        ), Subject(
            "Test", "TestIdC1", "C", PRIORITY.MUST, "Full Time", "1", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "20:00", "21:00", true)
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
        val result = obj?.findValidSearchCombinations(this.testSubjects) ?: throw Exception("obj is null")

        assert(result[0][0] == "B1")
        assert(result[0][1] == "A1")
        assert(result[0][2] == "C1")

        assert(result[1][0] == "B1")
        assert(result[1][1] == "A2")
        assert(result[1][2] == "C1")

        assert(result[2][0] == "B2")
        assert(result[2][1] == "A1")
        assert(result[2][2] == "C1")

        assert(result[3][0] == "B2")
        assert(result[3][1] == "A2")
        assert(result[3][2] == "C1")

        assert(result[4][0] == "B3")
        assert(result[4][1] == "A1")
        assert(result[4][2] == "C1")

        assert(result[5][0] == "B3")
        assert(result[5][1] == "A2")
        assert(result[5][2] == "C1")

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
    fun generateList(): Unit {
        val testObject: StudyPlanner = if (Objects.nonNull(obj)) obj!! else throw Exception("Obj is null")

        val result1 = testObject.generateCombinationList("A", 3, 2, 6)
        assertEquals(6, result1.size)
        assertEquals("A1", result1[0])
        assertEquals("A1", result1[1])
        assertEquals("A1", result1[2])
        assertEquals("A2", result1[3])
        assertEquals("A2", result1[4])
        assertEquals("A2", result1[5])

        val result2 = testObject.generateCombinationList("A", 1, 3, 6)
        assertEquals(6, result2.size)
        assertEquals("A1", result2[0])
        assertEquals("A2", result2[1])
        assertEquals("A3", result2[2])
        assertEquals("A1", result2[3])
        assertEquals("A2", result2[4])
        assertEquals("A3", result2[5])

        val result3 = testObject.generateCombinationList("A", 1, 3, 8)
        assertEquals(8, result3.size)
        assertEquals("A1", result3[0])
        assertEquals("A2", result3[1])
        assertEquals("A3", result3[2])
        assertEquals("A1", result3[3])
        assertEquals("A2", result3[4])
        assertEquals("A3", result3[5])
        assertEquals("A1", result3[6])
        assertEquals("A2", result3[7])


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
        result: Map<String, List<Subject>>, group: String, expectedSize: Int, expectedIds: List<String>
    ) {
        assertNotNull(result[group])
        assertEquals(expectedSize, result[group]?.size)

        val containsAllIds = result[group]?.all { it.id in expectedIds } ?: false
        assertTrue(containsAllIds)
    }
}