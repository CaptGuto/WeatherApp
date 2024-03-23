package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeeklyAdapter():RecyclerView.Adapter<WeeklyAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var DayTextView: TextView
        // TODO: make the container of variable length so it looks good.

        init{
            DayTextView = view.findViewById(R.id.tvDayOfWeek)
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_recycler_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.DayTextView.text = getTheDay(position+1)
    }

    private fun getTheDay(i: Int): CharSequence? {
        return when(i){
            1 -> "Mon"
            2 -> "Tuesday"
            3 -> "Wed"
            4 -> "Thur"
            5 -> "Fri"
            6 -> "Sat"
            7 -> "Sun"

            else -> "invalid Day Number"
        }
    }
}