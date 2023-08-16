import data.Subject

fun main(args: Array<String>) {
    println("------------------------------\nStudienplaner\n------------------------------\n")

    val path = "rsc/data2.json"
    val loader = JsonLoader(path)

    val subject: List<Subject> = loader.loadJson().subject
    println("Loaded Subject: ${subject.size}")


    val studyPlanner = StudyPlanner()
    studyPlanner.getStudyPlanVariationForMust(subject)


}