package com.example.weatherapp

import android.util.Log

class ChangeThemes {
    companion object{
        fun getLottieNumber(descriptionMain :String, descriptionDescription: String, currentTime: Long, sunSet: Long, sunRise: Long  ):Int{

            if(isNight(currentTime = currentTime, sunset = sunSet, sunrise = sunRise)){
                //Set the Loffie for Night Related
                return when (descriptionMain){
                    "" -> R.raw.clear_night

                    "cloud" -> {
                        if(true){
                            R.raw.cloudy_night
                        }
                        else{
                            R.raw.cloudy_night
                        }
                    }
                    "rain" -> R.raw.rainy_night
                    "thunderstorm" -> R.raw.thunder_storm
                    "clear" -> R.raw.clear_night
                    "snow" -> R.raw.snowy

                    else -> R.raw.cloudy_night
                }
            }
            else{
                //Set the loffie for Day related
                return when(descriptionMain){
                    "" -> R.raw.cloudy_night

                    "clouds" -> R.raw.light_clouds
                    "rain" -> R.raw.rainy_day
                    "thunderstorm" -> R.raw.thunder_storm
                    "clear" -> R.raw.sunny
                    "snow" -> R.raw.snowy

                    else -> R.raw.cloudy_windy
                }
            }

        }

        fun setTheame(currentTime: Long, sunSet: Long){
            // TODO:  make it change or help another function change the overall theme of the app i.e Light Mood and Dark Mood
        }

        private fun isNight(currentTime: Long, sunset: Long, sunrise: Long): Boolean{
            Log.i("tryit", "${currentTime > sunset}")

            return currentTime > sunset || currentTime < sunrise
        }


    }
}