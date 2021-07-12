package com.ngaid.mistakesdiary.presenter

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.ngaid.mistakesdiary.App
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun @receiver:ColorRes Int.toColor(): Int {
    return ContextCompat.getColor(App.c, this)
}

fun epochDayToMonthDd(dayInEpoch: Long): String {
    return LocalDate.ofEpochDay(dayInEpoch)
        .format(DateTimeFormatter.ofPattern("MMMM dd (EEE)"))
}