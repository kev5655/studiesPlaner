import java.text.SimpleDateFormat
import java.util.*

class TimeRange(private val day: String, private val t1: String, private val t2: String) {

    var time1: Date
    var time2: Date

    init {
        val dateFormat: SimpleDateFormat = SimpleDateFormat("ww-EEE-HH:mm", Locale.ENGLISH);

        println("$day - $t1 - $t2 ")

        time1 = dateFormat.parse("02-$day-$t1")
        time2 = dateFormat.parse("02-$day-$t2")
        if (time1 > time2) {
            val temp = time1
            time1 = time2
            time2 = temp
        }
    }


    fun isTimeBetween(t1: Date): Boolean {
        return t1 in time1..time2
    }

    companion object doTimeRangeOverlap {


        fun getTimeRangesFromDates(list: List<data.Date>): List<TimeRange> =
            list.map { TimeRange(it.weekDay, it.from, it.to) }
    }

}

fun rangeListOverlap(rangeList: List<TimeRange>): Boolean {
    for (range in rangeList) {
        val tempRangeList = rangeList.toMutableList()
        tempRangeList.remove(range)
        if (rangeListOverlap(tempRangeList, range)) {
            return true
        }
    }
    return false
}

fun rangeListOverlap(rangeList1: List<TimeRange>, rangeList2: List<TimeRange>): Boolean {
    for (range1 in rangeList1) {
        for (range2 in rangeList2) {
            if (doTimeRangeOverlap(range1, range2)) {
                return true
            }
        }
    }
    return false
}

fun rangeListOverlap(rangeList: List<TimeRange>, range: TimeRange): Boolean {
    return rangeList.any { doTimeRangeOverlap(it, range) }
}

fun doTimeRangeOverlap(range1: TimeRange, range2: TimeRange) =
    range1.isTimeBetween(range2.time1) || range1.isTimeBetween(range2.time2)