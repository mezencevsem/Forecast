package com.mezencevsem.forecast.views.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mezencevsem.forecast.R
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry
import com.mezencevsem.forecast.views.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance<FutureListWeatherViewModelFactory>()
    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FutureListWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        updateDateToNextTenDays()

        val futureWeatherEntries = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.location.await()

        weatherLocation.observe(viewLifecycleOwner, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeatherEntries.observe(viewLifecycleOwner, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer

            group_loading.visibility = View.GONE

            initRecyclerView(weatherEntries.toFutureWeatherItems())
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToNextTenDays() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            getString(R.string.next_10_days_text)
    }

    private fun List<FutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem> {
        val unitAbbreviation = if (viewModel.isMetricUnit) getString(R.string.celsius_sign)
        else getString(R.string.fahrenheit_sign)

        return this.map {
            FutureWeatherItem(it, unitAbbreviation, viewModel.isEnglishLanguage)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }
    }
}
