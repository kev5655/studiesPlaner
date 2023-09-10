import data.PRIORITY
import data.Subject
import utlis.printSubjectPlan

fun main(args: Array<String>) {
    println("------------------------------\nStudienplaner\n------------------------------\n")

    val path = "rsc/data2.json"
    val loader = JsonLoader(path)

    val subjects: List<Subject> = loader.loadJson().subject
    println("Loaded Subject: ${subjects.size}")

    val mustSubjects = subjects.filter { it.priority == PRIORITY.MUST }
    val optionalSubjects = subjects.filter { it.priority != PRIORITY.MUST }

    val studyPlanner = StudyPlanner()
    val must = getStudyPlanVariationForMust(mustSubjects)
    print("\n90909090909090909009099090909090909090909\n")
//    val optional = studyPlanner.getStudyPlanVariationForOptional(optionalSubjects)

    val result = studyPlanner.findStudyVariation(must, optionalSubjects)

    printSubjectPlan("Result", result)


}