
import java.text.SimpleDateFormat
import java.util.Date

class TimeRange(val day: String, private val t1: String, private val t2: String) {

    private var time1: Date;
    private var time2: Date;
    private var dateFormate: SimpleDateFormat = SimpleDateFormat("ww-EEE-HH:mm");

    init {
        time1 = dateFormate.parse("02-$day-$t1")
        time2 = dateFormate.parse("02-$day-$t2")
        if (time1 > time2) {
            val temp = time1
            time1 = time2
            time2 = temp
        }
//        println("Parsed Times: time1 ${time1}, time2 ${time2}")
    }

    fun isTimeBetween(t1: String): Boolean {
        val time = dateFormate.parse(t1);

        return time in time1..time2
    }

    fun isTimeBetween(t1: Date): Boolean {
        return t1 in time1..time2
    }

    companion object doTimeRangeOverlap {
        fun doTimeRangeOverlap(range1: TimeRange, range2: TimeRange) =
            range1.isTimeBetween(range2.time1) || range1.isTimeBetween(range2.time2)
    }

}