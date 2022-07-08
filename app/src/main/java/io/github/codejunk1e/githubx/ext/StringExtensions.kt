package io.github.codejunk1e.githubx.ext

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

fun getTimeDifference(timeStamp :String) :String{

    val date = DateTime(timeStamp, DateTimeZone.UTC).toDate()
    val timeNow = Calendar.getInstance().time

    var different: Long = timeNow.time - date?.time!!
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24
    val elapsedDays = different / daysInMilli

    different %= daysInMilli
    val elapsedHours = different / hoursInMilli

    different %= hoursInMilli
    val elapsedMinutes = different / minutesInMilli

    different %= minutesInMilli
    val elapsedSeconds = different / secondsInMilli

    return when {
        elapsedDays > 1 -> "$elapsedDays days ago"
        elapsedDays in 1..1 -> "$elapsedDays day ago"
        elapsedHours > 1 -> "$elapsedHours hours ago"
        elapsedHours in 1..1 -> "$elapsedHours hour ago"
        elapsedMinutes > 1 -> "$elapsedMinutes mins ago"
        elapsedMinutes in 1..1 -> "$elapsedMinutes min ago"
        elapsedSeconds > 1 -> "$elapsedSeconds seconds ago"
        elapsedSeconds in 1..1 -> "$elapsedSeconds second ago"
        else -> "now"
    }
}