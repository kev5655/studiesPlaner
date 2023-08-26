import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TimeRangeKtTest {

    private val overlapTimeRanges = listOf(
        TimeRange("Mon", "08:00", "09:00"),
        TimeRange("Thu", "08:00", "09:00"),
        TimeRange("Mon", "08:20", "08:45")
    )
    private val timeRanges = listOf(
        TimeRange("Mon", "08:00", "09:00"),
        TimeRange("Wed", "08:00", "09:00")
    )

    private val overlapTimeRange1 = TimeRange("Mon", "08:35", "08: 45")
    private val overlapTimeRange2 = TimeRange("Mon", "08:40", "08: 45")
    private val timeRange = TimeRange("Wed", "18:00", "19:00")

    @Test
    fun timeRange() {
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val times = listOf("00:00", "01:30", "08:45", "11:33", "12:45", "15:23", "20:50", "23:34")

        for (day in days) {
            for (timeIndex in 0 until times.size - 1) {
                println("Create TimeRange with: ${day} - ${times[timeIndex]} - ${times[timeIndex + 1]}")
                TimeRange(day, times[timeIndex], times[timeIndex + 1])
            }
        }
    }

    @Test
    fun rangeListOverlap() {

        var result = rangeListOverlap(overlapTimeRanges)
        assertTrue(result)

        result = rangeListOverlap(timeRanges)
        assertFalse(result)
    }

    @Test
    fun testRangeListOverlap() {
        var result = rangeListOverlap(overlapTimeRanges, listOf(overlapTimeRange1))
        assertTrue(result)
        result = rangeListOverlap(timeRanges, mutableListOf(timeRange))
        assertFalse(result)
    }

    @Test
    fun testRangeListOverlap1() {
        var result = rangeListOverlap(overlapTimeRanges, overlapTimeRange1);
        assertTrue(result)
        result = rangeListOverlap(timeRanges, timeRange)
        assertFalse(result)

    }

    @Test
    fun doTimeRangeOverlap() {
        var result = doTimeRangeOverlap(overlapTimeRange1, overlapTimeRange2);
        assertTrue(result)

        result = doTimeRangeOverlap(timeRange, overlapTimeRange1)
        assertFalse(result)

    }
}