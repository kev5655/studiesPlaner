package data

data class Subject(
    val name: String,
    val id: String,
    val subject: String,
    val priority: PRIORITY?,
    val studiesModel: String,
    val className: String,
    val startDay: String,
    val endDay: String,
    val dates: List<Date>,
)

enum class PRIORITY {
    MUST, LOW, MEDIUM, HIGH
}

fun getAllSubjectClasses(subjects: List<Subject>) = subjects.map { it.className }
fun getAllSubjectNames(subjects: List<Subject>) = subjects.map { it.subject }
fun getSubjectsByProperty(subjects: List<Subject>, predicate: (Subject) -> Boolean): List<Subject> =
    subjects.filter(predicate)

fun getSubjectBySubjectAndClass(subjects: List<Subject>, subjectName: String, _class: String) =
    subjects
        .filter { it.subject == subjectName }
        .find { it.className == _class }
        ?: throw Exception("subject not found subjectName: ${subjectName}, class: ${_class}")

fun getHowManyClassesHasSubject(subjects: List<Subject>, subjectName: String) =
    (getSubjectsByProperty(subjects) { it.subject == subjectName }).size

//fun groupSubjectsByNameAsList(subjects: List<Subject>): List<List<Subject>> {
//    val subjectNames = getAllSubjectNames(subjects).distinct()
//    println("Subject names: $subjectNames")
//    return subjectNames.map { subjectName ->
//        getSubjectsByProperty(subjects) { it.subject == subjectName }
//    }
//}
//fun groupSubjectsByNameAsMap(subjects: List<Subject>): Map<String, List<Subject>> {
//    val map = mutableMapOf<String, List<Subject>>()
//    val subjectNames = getAllSubjectNames(subjects).distinct()
//    subjectNames.forEach { subjectName ->
//        map[subjectName] = getSubjectsByProperty(subjects) { it.subject == subjectName }
//    }
//    return map
//}