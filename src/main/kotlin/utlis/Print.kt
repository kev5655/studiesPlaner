package utlis

import StudyPlan
import TimeRange
import data.Subject
import data.datesToString

fun printSubject(subject: Subject) =
    print("{ ${subject.subject} - ${subject.className} ${subject.name} - ${datesToString(subject.dates)} }, ")

fun printTimeRange(timeRange: TimeRange) {
    print("${timeRange}, ")
}

fun printSubjectPlan(name: String, list: List<StudyPlan>) {
    println("---------------------------------")
    println("$name - List Size: " + list.size)
    for (item in list) {
        print('[')
        item.subjects.forEach { printSubject(it) }
        print("] ----- [")
        item.timeRanges.forEach() { printTimeRange(it) }
        print(']')
        println()
    }
    println("---------------------------------")
}

fun printTemplate(name: String, template: List<List<String>>) {
    println("---------------------------------")
    println("$name - List Size: ${template.size}")
    for (list in template) {
        for (item in list) {
            print("[${item}] ")
        }
        println()
    }
    println("---------------------------------")
}


fun printReplaceTemplate(name: String, studyPlanVariation: List<List<Subject>>) {
    println("---------------------------------")
    println("$name - List Size: ${studyPlanVariation.size}")
    for (list in studyPlanVariation) {
        for (item in list) {
            printSubject(item)
        }
        println()
    }
    println("---------------------------------")

}