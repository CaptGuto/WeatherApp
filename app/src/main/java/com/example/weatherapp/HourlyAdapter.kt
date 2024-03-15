package com.example.weatherapp

import Hour
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Locale
import kotlin.math.roundToInt

class HourlyAdapter(private var forecastList: List<Hour>): RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var timeViewer:TextView
        var tempViewr: TextView
        var tempIcon: ImageView

        init {
            timeViewer = view.findViewById(R.id.tvHourlyHour)
            tempViewr = view.findViewById(R.id.tvHourlyTemp)
            tempIcon = view.findViewById(R.id.imageViewHourlyIcon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_recycler_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.timeViewer.text = formatTimeToAMPM(forecastList[position].time)
        holder.tempViewr.text = forecastList[position].temp_c.roundToInt().toString() + "\u00B0"


        val iconUrl = getIconUrl(forecastList[position].condition.icon)
        Glide.with(holder.itemView)
            .load(iconUrl)
            .into(holder.tempIcon)
    }

    // TODO: Maybe Change it into lottie image instead of just the icons
    private fun getIconUrl(iconCode: String): String {
        return "https:$iconCode" // Assuming iconCode starts with "//"
    }

    // TODO: Maybe move it to another place
    fun formatTimeToAMPM(timeString: String): String {
        // Parse the time string
        val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.getDefault())
        val date = dateFormat.parse(timeString)

        // Format the time to display only the hour in the AM/PM format
        val hourFormat = SimpleDateFormat("h a", Locale.getDefault())
        return hourFormat.format(date!!)
    }
}