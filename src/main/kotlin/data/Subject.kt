package data

data class Subject(
    val name: String,
    val id: String,
    val subject: String,
    val priority: PRIORITY?,
    val studiesModle: String,
    val className: String,
    val startDay: String,
    val endDay: String,
    val dates: List<Date>,
)

enum class PRIORITY {
    MUST, LOW, MEDIUM, HIGH
}