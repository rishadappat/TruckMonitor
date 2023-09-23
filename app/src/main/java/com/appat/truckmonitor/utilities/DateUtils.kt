package com.appat.truckmonitor.utilities

import android.text.format.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


enum class DateFormatString(val format: String) {
    defaultFormat("yyyy-MM-dd'T'HH:mm:ssZ"),
    DateTime("dd-MM-yyyy hh:mm aa"),
    Time("hh:mm aa")
}
object DateUtils {
    fun dateToDesc(
        dateString: String?,
        inputFormat: DateFormatString,
        outputFormat: DateFormatString
    ): String {
        if (dateString.isNullOrEmpty()) {
            return ""
        }
        val date = stringToDate(dateString, inputFormat) ?: return ""
        val nowTime = Calendar.getInstance(getLocaleForDate())
        val neededTime = Calendar.getInstance(getLocaleForDate())
        neededTime.time = date
        return if (neededTime[Calendar.YEAR] == nowTime[Calendar.YEAR]) {
            if (neededTime[Calendar.MONTH] == nowTime[Calendar.MONTH]) {
                when {
                    nowTime[Calendar.DATE] == neededTime[Calendar.DATE] -> {
                        "Today"
                    }
                    nowTime[Calendar.DATE] - neededTime[Calendar.DATE] == 1 -> {
                        "Yesterday"
                    }
                    nowTime[Calendar.DATE] - neededTime[Calendar.DATE] == 2 -> {
                        "2 Days ago"
                    }
                    else -> {
                        dateToString(date, outputFormat)
                    }
                }
            } else {
                dateToString(date, outputFormat)
            }
        } else {
            dateToString(date, outputFormat)
        }
    }


    fun dateToDescTime(
        dateString: String?,
        inputFormat: DateFormatString,
        dateOutputFormat: DateFormatString,
        timeOutputFormat: DateFormatString
    ): String {
        if (dateString.isNullOrEmpty()) {
            return ""
        }
        val date = stringToDate(dateString, inputFormat) ?: Date()
        return if (DateUtils.isToday(date.time)) {
            "Today ${dateToString(date, timeOutputFormat)}"
        } else if (isYesterday(date.time)) {
            "Yesterday ${
                dateToString(
                    date,
                    timeOutputFormat
                )
            }"
        }
        else
        {
            dateToString(
                date,
                dateOutputFormat
            )
        }
    }

    private fun dateToString(date: Date?, format: DateFormatString): String {
        if (date == null) {
            return ""
        }
        val df: DateFormat = SimpleDateFormat(format.format, getLocaleForDate())
        return df.format(date) ?: ""
    }

    private fun getLocaleForDate(): Locale {
        return Locale.getDefault()
    }

    fun stringToDate(dateString: String?, inputFormat: DateFormatString): Date? {
        if (dateString.isNullOrEmpty()) {
            return null
        }
        try {
            val df: DateFormat = SimpleDateFormat(inputFormat.format, getLocaleForDate())
            return df.parse(dateString) ?: return null
        }catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun isYesterday(whenInMillis: Long): Boolean {
        return DateUtils.isToday(whenInMillis + DateUtils.DAY_IN_MILLIS)
    }

}