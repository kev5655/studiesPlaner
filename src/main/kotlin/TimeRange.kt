import java.text.SimpleDateFormat
import java.util.*

class TimeRange(private val day: String, private val t1: String, private val t2: String) {

    private var time1: Date;
    private var time2: Date;
    private var dateFormat: SimpleDateFormat = SimpleDateFormat("ww-EEE-HH:mm");

    init {
        time1 = dateFormat.parse("02-$day-$t1")
        time2 = dateFormat.parse("02-$day-$t2")
        if (time1 > time2) {
            val temp = time1
            time1 = time2
            time2 = temp
        }
    }

    fun isTimeBetween(t1: String): Boolean {
        val time = dateFormat.parse(t1);

        return time in time1..time2
    }

    fun isTimeBetween(t1: Date): Boolean {
        return t1 in time1..time2
    }

    companion object doTimeRangeOverlap {
        fun doTimeRangeOverlap(range1: TimeRange, range2: TimeRange) =
            range1.isTimeBetween(range2.time1) || range1.isTimeBetween(range2.time2)

        fun getTimeRangesFromDates(list: List<data.Date>): List<TimeRange> =
            list.map { TimeRange(it.weekDay, it.from, it.to) }
    }

}