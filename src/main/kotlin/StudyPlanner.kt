import data.Date
import data.PRIORITY
import data.Subject
import kotlin.math.pow

class StudyPlanner(private val subjects: List<Subject>) {

    private val subjectsToDo: List<Subject> = getSubjectsByProperty(subjects) { it.priority == PRIORITY.MUST }
    private val subjectsOption: List<Subject> = getSubjectsByProperty(subjects) { it.priority != PRIORITY.MUST }

    fun bestPractice(): Output{

        println(subjectsToDo.size)
        println(subjectsOption.size)

        getValidVariation(subjectsToDo)


        return Output("Test", "Test", "Test")
    }

    private fun getSubjectsByProperty(subjects: List<Subject>, predicate: (Subject) -> Boolean): List<Subject> =
        subjects.filter(predicate)

    fun getValidVariation(subjects: List<Subject>): List<List<Subject>> {
        val combination: MutableList<MutableList<Subject>> = mutableListOf()
        val names = getAllSubjectNames(subjects).distinct();
        val classes = getAllSubjectClasses(subjects).distinct();
        val lenght = if (names.size > classes.size) names.size.toDouble()
            .pow(classes.size.toDouble()) else classes.size.toDouble()
            .pow(names.size.toDouble())

        val maxFistIt = (lenght / names.size).toInt()

        for (index in 0..maxFistIt) {
            val name = names[index]

        }

        val resultArray = mutableListOf<List<String>>()




        println(resultArray)

        return combination;
    }

    fun groupSubjectListAsList(subject: List<Subject>): List<List<Subject>> {
        val subjectNames = getAllSubjectNames(subjects).distinct();
        println(subjectNames)
        return subjectNames.map { subjectName ->
            getSubjectsByProperty(subjects) { it.subject == subjectName }
        }

    }

    fun groupSubjectListAsMap(subject: List<Subject>): Map<String, List<Subject>> {
        val map = mutableMapOf<String, List<Subject>>();
        val subjectNames = getAllSubjectNames(subjects).distinct();
        subjectNames.forEach { subjectName ->
            map[subjectName] = getSubjectsByProperty(subjects) { it.subject == subjectName }
        }
        return map
    }

    fun hasListAProperty(subjects: List<Subject>, property: String, condition: (Subject, String) -> Boolean): Boolean {
        return subjects.fold(false) { store, next ->
            if (store) return true
            condition(next, property)
        }
    }

    private fun getAllSubjectNames(subjects: List<Subject>) = subjects.map { it.subject }
    private fun getAllSubjectClasses(subjects: List<Subject>) = subjects.map { it.className }

    private fun printSubject(subject: Subject)
        = print("{ ${subject.subject} - ${subject.className} - ${datesToString(subject.dates)} } ")

    private fun datesToString(dates: List<Date>) = (dates.map { it -> "${it.weekDay} - ${it.from} - ${it.to}"})

}


data class Output(
    val subject: String,
    val day: String,
    val time: String,
)