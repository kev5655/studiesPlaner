package utlis

import SubjectsWidthTimeRange
import data.Subject
import data.datesToString

fun printSubject(subject: Subject) =
    print("[{ ${subject.subject} - ${subject.className} - ${datesToString(subject.dates)} }] ")

fun printSubjectPlan(name: String, list: List<SubjectsWidthTimeRange>) {
    println("---------------------------------")
    println("$name - List Size: " + list.size)
    for (item in list) {
        item.subjects.forEach { printSubject(it) }
        println()
    }
    println("---------------------------------")
}

fun printTemplate(template: List<List<String>>) {
    println("---------------------------------")
    println("Subject Template")
    for (list in template) {
        for (item in list) {
            print("[${item}] ")
        }
        println()
    }
    println("---------------------------------")
}


fun printReplaceTemplate(studyPlanVariation: List<List<Subject>>) {
    println("---------------------------------")
    println("Replaced Template with Subjects")
    for (list in studyPlanVariation) {
        for (item in list) {
            printSubject(item)
        }
        println()
    }
    println("---------------------------------")

}