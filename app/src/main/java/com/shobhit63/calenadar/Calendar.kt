package com.shobhit63.calenadar

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.shobhit63.calenadar.databinding.FragmentCalndarBinding
import timber.log.Timber
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*


class Calendar : Fragment() {
    private lateinit var _binding:FragmentCalndarBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCalndarBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(1000)
        val lastMonth = currentMonth.plusMonths(1000)

        val daysOfWeek = daysOfWeek()
        binding.myCalendar.setup(firstMonth, lastMonth, daysOfWeek().first())
        binding.myCalendar.scrollToMonth(currentMonth)

        binding.myCalendar.monthScrollListener = { month ->
            binding.monthYearText.text = getString(R.string.month_year,month.yearMonth.month.toString(),month.yearMonth.year.toString())
            Timber.d("Month = ${month.yearMonth.month}")
        }
        binding.myCalendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    container.textView.setTextColor(ContextCompat.getColor(requireActivity(),R.color.text_color))

                } else {
                    container.textView.setTextColor(Color.GRAY)

                }
            }
        }

        binding.myCalendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)

                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    val titlesContainer = container.titlesContainers
                    titlesContainer.children
                        .map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek[index]
                            val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                            textView.text = title
                        }
                }
            }

        binding.nextMonthImage.setOnClickListener {
            binding.myCalendar.findFirstVisibleMonth()?.let {
                binding.myCalendar.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        binding.previousMonthImage.setOnClickListener {
            binding.myCalendar.findFirstVisibleMonth()?.let {
                binding.myCalendar.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }


    }


}