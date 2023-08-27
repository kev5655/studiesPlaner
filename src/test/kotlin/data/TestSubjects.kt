package data

import StudyPlan
import TimeRange

val notValidTestSubjects: List<Subject> = listOf(
    Subject(
        "Test", "TestIdA1", "Math", PRIORITY.MUST, "Full Time", "A1", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "08:00", "10:00", true),
            Date("Thu", "15:00", "17:00", true),
        )
    ), Subject(
        "Test", "TestIdA2", "Math", PRIORITY.MUST, "Full Time", "A2", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "19:00", "20:00", true)
        )
    ), Subject(
        "Test", "TestIdB1", "C++", PRIORITY.MUST, "Full Time", "B1", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "9:00", "11:00", true)
        )
    ), Subject(
        "Test", "TestIdB2", "C++", PRIORITY.MUST, "Full Time", "B2", "01.01.2023", "02.02.2023", listOf(
            Date("Mon", "11:00", "13:00", true)
        )
    ), Subject(
        "Test", "TestIdB3", "C++", PRIORITY.MUST, "Full Time", "B3", "01.01.2023", "02.02.2023", listOf(
            Date("Thu", "20:00", "21:00", true)
        )
    ), Subject(
        "Test", "TestIdC1", "Algo", PRIORITY.MUST, "Full Time", "C1", "01.01.2023", "02.02.2023", listOf(
            Date("Fri", "20:00", "21:00", true)
        )
    )
)


val groupedSubjectNotValid: List<List<Subject>> = listOf(
    listOf(
        Subject(
            "Test", "TestIdA1", "Math", PRIORITY.MUST, "Full Time", "one", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Mon", "11:00", "12:00", true)
            )
        ),
        Subject(
            "Test", "TestIdA2", "Math", PRIORITY.MUST, "Full Time", "two", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "09:00", "11:00", true)
            )
        )
    )
)

val groupedSubjectValid: List<List<Subject>> = listOf(
    listOf(
        Subject(
            "Test", "TestIdA1", "Math", PRIORITY.MUST, "Full Time", "one", "01.01.2023", "02.02.2023", listOf(
                Date("Mon", "08:00", "10:00", true),
                Date("Mon", "11:00", "12:00", true)
            )
        ),
        Subject(
            "Test", "TestIdA2", "Math", PRIORITY.MUST, "Full Time", "two", "01.01.2023", "02.02.2023", listOf(
                Date("Wed", "09:00", "11:00", true)
            )
        )
    )
)

val validStudyPLanMustAndOptional = listOf(
    listOf(
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "Math", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Mon", "08:00", "12:00"))
        ),
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "Pat", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Mon", "08:00", "12:00"))
        )
    ),
    listOf(
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "C++", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                ),
                Subject(
                    "Test", "TestId", "OS", PRIORITY.MUST, "Full Time", "I2q", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Thu", "08:00", "12:00"), TimeRange("Thu", "08:00", "12:00"))
        ),
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "P2wJ", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                ),
                Subject(
                    "Test", "TestId", "WP", PRIORITY.MUST, "Full Time", "I2q", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Thu", "08:00", "12:00"), TimeRange("Thu", "08:00", "12:00"))
        )
    )
)

val notValidStudyPLanMustAndOptional = listOf(
    listOf(
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "Math", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Mon", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Mon", "08:00", "12:00"))
        ),
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "Pat", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Mon", "20:00", "22:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Mon", "20:00", "22:00"))
        )
    ),
    listOf(
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "C++", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Mon", "08:30", "11:00", true)
                    )
                ),
                Subject(
                    "Test", "TestId", "OS", PRIORITY.MUST, "Full Time", "I2q", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Mon", "08:30", "11:00"), TimeRange("Thu", "08:00", "12:00"))
        ),
        StudyPlan(
            mutableListOf(
                Subject(
                    "Test", "TestId", "P2wJ", PRIORITY.MUST, "Full Time", "I2p", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                ),
                Subject(
                    "Test", "TestId", "WP", PRIORITY.MUST, "Full Time", "I2q", "01.01.2023", "02.02.2023",
                    listOf(
                        Date("Thu", "08:00", "12:00", true)
                    )
                )
            ), mutableListOf(TimeRange("Thu", "08:00", "12:00"), TimeRange("Thu", "08:00", "12:00"))
        )
    )
)