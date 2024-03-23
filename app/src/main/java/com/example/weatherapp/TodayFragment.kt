package com.example.weatherapp

import Hour
import Location
import Weather
import WeatherAPIResponse
import android.Manifest
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentTodayBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
private const val LOCATION_REQUEST_CODE = 300
//Initialize for ViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [TodayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _binding: FragmentTodayBinding
    private val binding get() = _binding!!

    private lateinit var loca: android.location.Location

    //Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val viewModel: TodayViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TodayViewModel::class.java)
    }




    lateinit var adapter: HourlyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTodayBinding.bind(requireView())

        checkLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : android.location.Location? ->
                Log.i("tryiit", location.toString())
                if (location != null) {
                    loca = location
                    Log.i("tryiit", "${location.latitude} ${location.longitude}")
                    viewModel.fetchWeather(location.latitude, location.longitude)

                    viewModel.fetchHourlyForecast("${location.latitude},${location.longitude}")



                }
            }
        viewModel.hourlyForecast.observe(requireActivity()) { weather ->

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewHourly)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = layoutManager

            adapter = HourlyAdapter(weather.forecast.forecastday[0].hour)
            recyclerView.adapter = adapter
            val today = weather.forecast.forecastday[0]
            for (hour in today.hour) {
                Log.i("tryyit", hour.time + hour.condition.text + hour.temp_c + hour.humidity)

            }




            var returnValue = formatTimeToHour(weather.location.localtime)

            val targetPosition = (returnValue+3) - (3 / 2)

            // Scroll to the target position
            recyclerView.layoutManager?.smoothScrollToPosition(recyclerView, RecyclerView.State(), targetPosition)
            // TODO: Increase the Size of the View Selected

        }


        // Observe weather data
        viewModel.weather.observe(requireActivity(), Observer { weather ->
            // Do something with the weather data
            Log.i("tryit", weather.toString())
            updateUI(weather)
            binding.swipeRefreshLayout.isRefreshing = false
        })

        viewModel.hourlyForecast.observe(requireActivity()) { hourlyweather ->
            Log.i("tryyit", hourlyweather.toString())
        }



        //OnSwipe Refresh
        binding.swipeRefreshLayout.setOnRefreshListener{
            loca.let { viewModel.fetchWeather(loca.latitude, loca.longitude) }
            viewModel.fetchHourlyForecast("${loca.latitude},${loca.longitude}")
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()

        Log.i("tryit", "Exited")
//        _binding = null
    }

    // TODO: Move it to another class with the lottie pickers
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI(weather: Weather) {
        //update the ui of the fragment after the api call
        binding.tvMainTemperature.text = "${((weather.main.temp)-273.15).roundToInt()}\u00B0"
        binding.tvDate.text = "${formatUnixTimestamp(weather.dt)}"
        binding.tvWindSpeedToday.text = "${(weather.wind.speed).roundToInt()}km/h"
//        binding.tvChanceOfRainfall.text = "${}"
        binding.tvHumidity.text = "${(weather.main.humidity).roundToInt()}%"

        //Update Loffie Image
        binding.LoffieMain.setAnimation(ChangeThemes.getLottieNumber(weather.weather[0].main.lowercase(), weather.weather[0].description.lowercase(), weather.dt, weather.sys.sunset, weather.sys.sunrise))
        binding.LoffieMain.playAnimation()
    }


    fun updateForecastData(forecastData: List<Hour>) {
//        adapter.updateData(forecastData)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatUnixTimestamp(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMM", Locale.ENGLISH)
        return instant.atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(requireActivity(), LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED){
            Log.i("tryiit", "Permission Granted")
        }
        else{
            Log.i("tryiit", "Asking Permission")
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(LOCATION_PERMISSION), LOCATION_REQUEST_CODE)
        }
    }

    fun formatTimeToHour(timeString: String): Int {
        // Parse the time string
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = dateFormat.parse(timeString)

        // Format the time to display only the hour
        val hourFormat = SimpleDateFormat("HH", Locale.getDefault())
        return hourFormat.format(date!!).toInt()
    }
}