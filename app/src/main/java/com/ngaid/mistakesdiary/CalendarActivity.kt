package com.ngaid.mistakesdiary

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.ngaid.mistakesdiary.databinding.ActivityCalendarBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class CalendarActivity : AppCompatActivity() {              //TODO:refactor

    var selectedDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b: ActivityCalendarBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_calendar)

        setSupportActionBar(b.toolbar)

        setCalender(b.calendar)

        b.addMistakeBtn.setOnClickListener {
            onClickAddMistake()
        }
    }

    private fun setNewDateFragment() {
        val currentMistakes = MistakesListFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainer, currentMistakes)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        //ft.addToBackStack(null)
        ft.commit()
    }

    private fun onClickAddMistake() {
        val intent = Intent(this, MistakeCreationView::class.java)
        startActivity(intent)
    }

    private fun setCalender(calendar: CalendarView) {
        val currentMonth = YearMonth.now()
        calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(c: DayViewContainer, day: CalendarDay) {
                c.day = day
                c.calendar = calendar
                c.dayView.text = day.date.dayOfMonth.toString()
                when {
                    day.date == LocalDate.now() -> c.dayView.setTextColor(Color.CYAN)
                    day.owner == DayOwner.THIS_MONTH -> c.dayView.setTextColor(Color.WHITE)
                    else -> c.dayView.setTextColor(Color.GRAY)
                }
                if (day.date == selectedDate) c.selectionView.visibility = View.VISIBLE
                else c.selectionView.visibility = View.INVISIBLE
            }
        }
        calendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.monthYearView.text = "${month.yearMonth.month} ${month.year}"
            }
        }
        calendar.setup(
            startMonth = YearMonth.of(2000, 1),
            endMonth = currentMonth.plusMonths(12),
            firstDayOfWeek = DayOfWeek.MONDAY
        )          //first day of week setting
        calendar.scrollToMonth(currentMonth)
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val dayView: TextView = view.findViewById(R.id.calendarDayText)
        val selectionView: View = view.findViewById(R.id.selectionV)
        lateinit var calendar: CalendarView
        lateinit var day: CalendarDay

        init {
            view.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH && day.date != selectedDate) {
                    App.currentDateInEpoch = day.date.toEpochDay()
                    setNewDateFragment()

                    val prevSelectedDate = selectedDate
                    selectedDate = day.date
                    calendar.notifyDateChanged(day.date)
                    calendar.notifyDateChanged(prevSelectedDate)
                }
            }
        }
    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val monthYearView: TextView = view.findViewById(R.id.monthYearTV)
    }
}