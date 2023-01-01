package com.shobhit63.calenadar

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView: TextView = view.findViewById<TextView>(R.id.calendarDayText)



}

class MonthViewContainer(view: View) : ViewContainer(view) {
    val titlesContainers: ViewGroup = view.findViewById<ViewGroup>(R.id.legendLayout) }

