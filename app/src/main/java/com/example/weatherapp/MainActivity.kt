package com.example.weatherapp

import Location
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
private const val LOCATION_REQUEST_CODE = 300


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    //Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initiate the binding for the activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        //Start the Today Fragment When the app Launches
        val todayFragment = TodayFragment()
        replaceFragment(todayFragment)
        val googleApiAvailability = GoogleApiAvailability.getInstance()



        Log.i("tryiit",  googleApiAvailability.isGooglePlayServicesAvailable(this).toString())
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
        Log.i("tryiit",(resultCode == ConnectionResult.SUCCESS).toString())

        //Onclick Activity for the bottom navigation go change the fragments
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.todayMenuItem ->{
                    val fragment = TodayFragment()
                    replaceFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.thisWeekMenuItem ->{
                    val fragment = ThisWeekFragment()
                    replaceFragment(fragment)
                    return@setOnItemSelectedListener true
                }
                R.id.watchMenuItem ->{
                    val fragment = WatchFragment()
                    replaceFragment(fragment)
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }

        }






    }

    fun checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(this, LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED){
            Log.i("tryiit", "Permission Granted")
        }
        else{
            Log.i("tryiit", "Asking Permission")
            ActivityCompat.requestPermissions(this, arrayOf(LOCATION_PERMISSION), LOCATION_REQUEST_CODE)
        }
    }


    //A function to replace the frameLayout with new fragment whenever promted
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }


    // TODO: Maybe change the location
    fun askLocationPermission(){

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_REQUEST_CODE){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i("tryiit", "Permission Granted")
            }
            else{
                Log.i("tryiit", "Permission Denide")
            }
        }
    }

}