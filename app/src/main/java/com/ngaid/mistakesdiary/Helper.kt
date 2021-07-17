package com.ngaid.mistakesdiary

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun @receiver:ColorRes Int.toColor(): Int {
    return ContextCompat.getColor(App.c, this)
}

fun epochDayToMonthDd(dayInEpoch: Long): String {
    return LocalDate.ofEpochDay(dayInEpoch)
        .format(DateTimeFormatter.ofPattern("MMMM dd (EEE)"))
}