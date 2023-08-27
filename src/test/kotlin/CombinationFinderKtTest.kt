import data.Date
import data.PRIORITY
import data.Subject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utlis.printTemplate


class CombinationFinderKtTest {

    private val testSubjects1 = listOf(
        Subject(
            "Test", "TestI1p", "Math", PRIORITY.MUST, "Full Time", "I1p", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ), Subject(
            "Test", "TestIdI1q", "Math", PRIORITY.MUST, "Full Time", "I1q", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "11:00", "12:00", true)
            )
        ), Subject(
            "Test", "TestIdI1p", "C++", PRIORITY.MUST, "Full Time", "I1p", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "9:00", "11:00", true)
            )
        ), Subject(
            "Test", "TestIdI1q", "C++", PRIORITY.MUST, "Full Time", "I1q", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "11:00", "13:00", true)
            )
        ), Subject(
            "Test", "TestIdI1p", "Algo", PRIORITY.MUST, "Full Time", "I1p", "01.01.2023", "02.02.2023", listOf(
                Date("Fri", "20:00", "21:00", true)
            )
        )
    )
    private val testSubjects2 = listOf(
        Subject(
            "Test", "TestI2p-I2q", "P2wJ", PRIORITY.LOW, "Full Time", "I2p-I2q", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ),
        Subject(
            "Test", "TestI2p-I2q", "PaT", PRIORITY.LOW, "Full Time", "I2p-I2q", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ),
        Subject(
            "Test", "TestI2p-I2q", "WP", PRIORITY.LOW, "Full Time", "I2p", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ),
        Subject(
            "Test", "TestI2p-I2q", "WP", PRIORITY.LOW, "Full Time", "I2q", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ),
        Subject(
            "Test", "TestI2p-I2q", "OS", PRIORITY.LOW, "Full Time", "I2p", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ),
        Subject(
            "Test", "TestI2p-I2q", "OS", PRIORITY.LOW, "Full Time", "I2q", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Thu", "15:00", "17:00", true)
            )
        ),
    )


    @Test
    fun findCombinationTemplate() {
        val result1 = findCombinationTemplate(testSubjects1, "-")

        printTemplate("Test Template", result1)

        assertEquals(4, result1.size)
        assertEquals(3, result1[0].size)

        assertEquals("Math-I1p", result1[0][0])
        assertEquals("C++-I1p", result1[0][1])
        assertEquals("Algo-I1p", result1[0][2])

        assertEquals("Math-I1p", result1[1][0])
        assertEquals("C++-I1q", result1[1][1])
        assertEquals("Algo-I1p", result1[1][2])

        assertEquals("Math-I1q", result1[2][0])
        assertEquals("C++-I1p", result1[2][1])
        assertEquals("Algo-I1p", result1[2][2])

        assertEquals("Math-I1q", result1[3][0])
        assertEquals("C++-I1q", result1[3][1])
        assertEquals("Algo-I1p", result1[3][2])

        val result2 = findCombinationTemplate(testSubjects2, "#")
        printTemplate("Test Template", result2)

        assertEquals(4, result2.size)
        assertEquals(3, result2[0].size)

        assertEquals("WP#I2p", result2[0][0])
        assertEquals("OS#I2p", result2[0][1])
        assertEquals("P2wJ#I2p-I2q", result2[0][2])
        assertEquals("PaT#I2p-I2q", result2[0][3])

        assertEquals("WP#I2p", result2[1][0])
        assertEquals("OS#I2q", result2[1][1])
        assertEquals("P2wJ#I2p-I2q", result2[1][2])
        assertEquals("PaT#I2p-I2q", result2[1][3])

        assertEquals("WP#I2q", result2[2][0])
        assertEquals("OS#I2p", result2[2][1])
        assertEquals("P2wJ#I2p-I2q", result2[2][2])
        assertEquals("PaT#I2p-I2q", result2[2][3])

        assertEquals("WP#I2q", result2[3][0])
        assertEquals("OS#I2q", result2[3][1])
        assertEquals("P2wJ#I2p-I2q", result2[3][2])
        assertEquals("PaT#I2p-I2q", result2[3][3])
    }

    @Test
    fun generateCombinationTemplateOfOneSequence() {
        val classes1 = listOf<String>("X1", "Y2")
        val classes2 = listOf<String>("1", "2", "3")


        val result1 = generateCombinationTemplateOfOneSequence("A", classes1, 3, 2, 6, "-")
        assertEquals(6, result1.size)
        assertEquals("A-X1", result1[0])
        assertEquals("A-X1", result1[1])
        assertEquals("A-X1", result1[2])
        assertEquals("A-Y2", result1[3])
        assertEquals("A-Y2", result1[4])
        assertEquals("A-Y2", result1[5])

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