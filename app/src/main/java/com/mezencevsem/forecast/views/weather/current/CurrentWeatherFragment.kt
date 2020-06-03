package com.mezencevsem.forecast.views.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mezencevsem.forecast.R
import com.mezencevsem.forecast.data.database.entity.CurrentWeatherEntry
import com.mezencevsem.forecast.internal.glide.GlideApp
import com.mezencevsem.forecast.views.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance<CurrentWeatherViewModelFactory>()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        updateDateToToday()

        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.location.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            if (!viewModel.isEnglishLanguage && it.weatherDescriptionsRu == null) return@Observer

            group_loading.visibility = View.GONE

            updateCondition(getLocalizedCondition(it))
            updateTemperatures(it.temperature, it.feelslike)
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons.first())
                .into(imageView_condition_icon)
        })
    }

    private fun getLocalizedCondition(weather: CurrentWeatherEntry): String {
        return if (viewModel.isEnglishLanguage) weather.weatherDescriptions.first()
        else weather.weatherDescriptionsRu!!.first()
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.today)
    }

    private fun updateTemperatures(temperature: Double, feelsLikeTemperature: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(
            getString(R.string.celsius_sign),
            getString(R.string.fahrenheit_sign))
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = getString(R.string.feels_like_text) + " $feelsLikeTemperature$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(
            getString(R.string.kilometers_per_hour_abbreviation_text),
            getString(R.string.miles_per_hour_abbreviation_text))
        textView_wind.text = getString(R.string.wind_text) + ": $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(
            getString(R.string.millimeters_abbreviation_text),
            getString(R.string.inch_abbreviation_text))
        textView_precipitation.text = getString(R.string.precipitation_text) + ": $precipitationVolume $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation(
            getString(R.string.kilometers_abbreviation_text),
            getString(R.string.miles_abbreviation_text))
        textView_visibility.text = getString(R.string.visibility_text) + ": $visibilityDistance $unitAbbreviation"
    }
}
