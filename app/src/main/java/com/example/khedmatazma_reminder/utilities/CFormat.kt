package com.example.khedmatazma_reminder.utilities


/**
 * Created by saeed on 5/4/2018.
 */

class CFormat {
    fun getServerDateFormat(year: Int, month: Int, day: Int): String {
        var month = month
        return year.toString() + "-" + (if (++month < 10) "0$month" else month) + "-" + if (day < 10) "0$day" else day
    }

    fun oneDigitToTwoDigit(num: String): String {
        if (num.length == 1) {
            return "0$num"
        } else if (num.length == 0) {
            return "00"
        }
        return num
    }


}
