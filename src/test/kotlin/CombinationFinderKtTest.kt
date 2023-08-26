import data.testSubjects
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class CombinationFinderKtTest {

    private val subject = testSubjects


    @Test
    fun findCombinationTemplate() {
        val result = findCombinationTemplate(subject, "-")

        assertEquals("C++-B1", result[0][0])
        assertEquals("Math-A1", result[0][1])
        assertEquals("Algo-1", result[0][2])

        assertEquals("C++-B1", result[1][0])
        assertEquals("Math-A2", result[1][1])
        assertEquals("Algo-1", result[1][2])

        assertEquals("C++-B2", result[2][0])
        assertEquals("Math-A1", result[2][1])
        assertEquals("Algo-C1", result[2][2])

        assertEquals("C++-B2", result[3][0])
        assertEquals("Math-A2", result[3][1])
        assertEquals("Algo-C1", result[3][2])

        assertEquals("C++-B3", result[4][0])
        assertEquals("Math-A1", result[4][1])
        assertEquals("Algo-C1", result[4][2])

        assertEquals("C++-B3", result[5][0])
        assertEquals("Math-A2", result[5][1])
        assertEquals("Algo-C1", result[5][2])

    }

    @Test
    fun generateCombinationTemplateOfOneSequence() {
        val classes1 = listOf<String>("1", "2")
        val classes2 = listOf<String>("1", "2", "3")


        val result1 = generateCombinationTemplateOfOneSequence("A", classes1, 3, 2, 6, "-")
        assertEquals(6, result1.size)
        assertEquals("A-1", result1[0])
        assertEquals("A-1", result1[1])
        assertEquals("A-1", result1[2])
        assertEquals("A-2", result1[3])
        assertEquals("A-2", result1[4])
        assertEquals("A-2", result1[5])

        val result2 = generateCombinationTemplateOfOneSequence("A", classes2, 1, 3, 6, "-")
        assertEquals(6, result2.size)
        assertEquals("A-1", result2[0])
        assertEquals("A-2", result2[1])
        assertEquals("A-3", result2[2])
        assertEquals("A-1", result2[3])
        assertEquals("A-2", result2[4])
        assertEquals("A-3", result2[5])

        val result3 = generateCombinationTemplateOfOneSequence("A", classes2, 1, 3, 8, "-")
        assertEquals(8, result3.size)
        assertEquals("A-1", result3[0])
        assertEquals("A-2", result3[1])
        assertEquals("A-3", result3[2])
        assertEquals("A-1", result3[3])
        assertEquals("A-2", result3[4])
        assertEquals("A-3", result3[5])
        assertEquals("A-1", result3[6])
        assertEquals("A-2", result3[7])

    }

}