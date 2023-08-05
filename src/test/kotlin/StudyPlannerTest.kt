import data.Date
import data.PRIORITY
import data.Subject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StudyPlannerTest {
    private val testSubjects: List<Subject> =
        listOf(
            Subject(
                "Test",
                "TestId",
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
                "TestId",
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
                "TestId",
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
                "TestId",
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
                "TestId",
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
                "TestId",
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
    fun getValidVariation(testSubjects: List<Subject>) {

        val result = obj?.getValidVariation(this.testSubjects) : throw Exception("Hi There!")




        assert(result[0][0].subject == "A")
        assert(result[0][0].className == "1")
        assert(result[0][1].subject == "B")
        assert(result[0][1].className == "1")
        assert(result[0][2].subject == "C")
        assert(result[0][2].className == "1")




    }
}