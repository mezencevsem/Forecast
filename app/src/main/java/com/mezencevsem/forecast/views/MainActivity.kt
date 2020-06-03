package com.mezencevsem.forecast.views

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.mezencevsem.forecast.R
import com.mezencevsem.forecast.data.provider.LanguageProvider
import com.mezencevsem.forecast.data.provider.LanguageProviderImpl
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.*

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance<FusedLocationProviderClient>()
    private lateinit var languageProvider: LanguageProvider

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLanguage()
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

    private fun setupLanguage() {
        languageProvider = LanguageProviderImpl(this)
        val locale = Locale(languageProvider.getLanguageCode())
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, null)
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
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
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
                Toast.makeText(this, getString(R.string.location_permission_toast_text), Toast.LENGTH_LONG).show()
        }
    }
}
