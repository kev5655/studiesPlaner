import data.Date
import data.PRIORITY
import data.Subject

val testSubjects: List<Subject> = listOf(
    Subject(
        "Test", "TestIdA1", "Math", PRIORITY.MUST, "Full Time", "1", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "08:00", "10:00", true),
            Date("Thu", "15:00", "17:00", true),
        )
    ), Subject(
        "Test", "TestIdA2", "Math", PRIORITY.MUST, "Full Time", "2", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "11:00", "12:00", true)
        )
    ), Subject(
        "Test", "TestIdB1", "C++", PRIORITY.MUST, "Full Time", "1", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "9:00", "11:00", true)
        )
    ), Subject(
        "Test", "TestIdB2", "C++", PRIORITY.MUST, "Full Time", "2", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "11:00", "13:00", true)
        )
    ), Subject(
        "Test", "TestIdB3", "C++", PRIORITY.MUST, "Full Time", "3", "01.01.2023", "02.02.2023", listOf(
            Date("Thu", "20:00", "21:00", true)
        )
    ), Subject(
        "Test", "TestIdC1", "Algo", PRIORITY.MUST, "Full Time", "1", "01.01.2023", "02.02.2023", listOf(
            Date("Fri", "20:00", "21:00", true)
        )
    )
)