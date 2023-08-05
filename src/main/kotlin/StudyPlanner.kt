import data.Date
import data.PRIORITY
import data.Subject

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
        for((index, first) in subjects.withIndex()){
            combination.add(mutableListOf<Subject>())
            combination[index].add(first)

            for(second in subjects){
                if(first.id === second.id) continue
                if(hasListAProperty(combination[index], second)) continue
                combination[index].add(second)
            }

        }

        //for((index, fist) in subjects.withIndex()){
        //    combination.add(mutableListOf<Subject>())
        //    combination[index].add(fist)
        //    for(second in subjects){
        //        if(fist.subject === second.subject) continue
        //        if((combination[index].map { it -> it.subject }).contains(second.subject)) continue

        //        combination[index].add(second)
        //    }
        //}

        for (list in combination){
            list.sortBy { it.subject }
            for(item in list){
                printSubject(item)
            }
            println()
        }
        return combination;
    }

    private fun hasListAProperty(subjects: List<Subject>, match: Subject)
        = subjects.contains(match)

    private fun getAllSubjectNames(subjects: List<Subject>) = subjects.map { it.subject }

    private fun printSubject(subject: Subject)
        = print("{ ${subject.subject} - ${subject.className} - ${datesToString(subject.dates)} } ")

    private fun datesToString(dates: List<Date>) = (dates.map { it -> "${it.weekDay} - ${it.from} - ${it.to}"})
}


data class Output(
    val subject: String,
    val day: String,
    val time: String,
)