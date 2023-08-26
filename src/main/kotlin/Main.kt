import data.PRIORITY
import data.Subject

fun main(args: Array<String>) {
    println("------------------------------\nStudienplaner\n------------------------------\n")

    val path = "rsc/data2.json"
    val loader = JsonLoader(path)

    val subjects: List<Subject> = loader.loadJson().subject
    println("Loaded Subject: ${subjects.size}")

    val mustSubjects = subjects.filter { it.priority == PRIORITY.MUST }
    val optionalSubjects = subjects.filter { it.priority != PRIORITY.MUST }

    val studyPlanner = StudyPlanner()
    studyPlanner.getStudyPlanVariationForMust(mustSubjects)
    studyPlanner.getStudyPlanVariationForOptional(optionalSubjects)



}