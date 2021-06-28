package com.ngaid.mistakesdiary.presenter

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.ngaid.mistakesdiary.App
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(App.c, id)
}

fun epochDayToMonthDd(dayInEpoch: Long): String {
    return LocalDate.ofEpochDay(dayInEpoch)
        .format(DateTimeFormatter.ofPattern("MMMM dd (EEE)"))
}