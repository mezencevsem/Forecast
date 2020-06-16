package com.mezencevsem.forecast.views

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.mezencevsem.forecast.R
import com.mezencevsem.forecast.data.provider.LanguageProviderImpl
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.*

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware,
    SharedPreferences.OnSharedPreferenceChangeListener {
    override val kodein by closestKodein()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance<FusedLocationProviderClient>()
    private lateinit var navController: NavController
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.registerOnSharedPreferenceChangeListener(this)
        changeLanguage()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.navigation_host_fragment)

        bottom_navigation.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)

        requestLocationPermission()

        if (hasLocationPermission())
            bindLocationManager()
        else
            requestLocationPermission()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient,
            locationCallback
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //TODO bug
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
            else
                Toast.makeText(
                    this,
                    getString(R.string.location_permission_toast_text),
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    //TODO bug change theme lead to dropping language
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            "USE_DEVICE_LOCATION" -> Log.i("PreferenceChanged", "USE_DEVICE_LOCATION")
            "CUSTOM_LOCATION" -> Log.i("PreferenceChanged", "CUSTOM_LOCATION")
            "LANGUAGE" -> changeLanguage()
            "UNIT_SYSTEM" -> Log.i("PreferenceChanged", "UNIT_SYSTEM")
            "DARK_THEME" -> changeColorTheme(sharedPreferences)
        }
    }

    private fun changeColorTheme(sharedPreferences: SharedPreferences) {
        val isDarkTheme: Boolean = sharedPreferences.getBoolean("DARK_THEME", false)
        if (isDarkTheme) setColorTheme(AppCompatDelegate.MODE_NIGHT_YES)
        else setColorTheme(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setColorTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    private fun changeLanguage() {
        val languageProvider = LanguageProviderImpl(applicationContext)
        val locale = Locale(languageProvider.getLanguageCode())
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
