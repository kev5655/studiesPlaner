package data

data class Date(
    val weekDay: String,
    val from: String,
    val to: String,
    val online: Boolean,
)

fun datesToString(dates: List<Date>) = (dates.map { "${it.weekDay} - ${it.from} - ${it.to}" })

