package com.mezencevsem.forecast.views.weather.future.list

import com.mezencevsem.forecast.R
import com.mezencevsem.forecast.data.database.entity.FutureWeatherEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*


class FutureWeatherItem(
    private val weatherEntry: FutureWeatherEntry,
    private val unitAbbreviation: String,
    private val isEnglishLanguage: Boolean
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateDate()
            updateTemperature()
            updateTemperatureRange()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateTemperature() {
        textView_temperature.text = weatherEntry.avgtemp.toString().plus(unitAbbreviation)
    }

    private fun ViewHolder.updateTemperatureRange() {
        textView_temperature_range.text = weatherEntry.mintemp.toString() + unitAbbreviation +
                " .. " + weatherEntry.maxtemp.toString() + unitAbbreviation
    }

    private fun ViewHolder.updateDate(){
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(if (isEnglishLanguage) Locale.ENGLISH else Locale.getDefault())
        val date = LocalDate.parse(weatherEntry.date, DateTimeFormatter.ISO_LOCAL_DATE)
        textView_date.text = date.format(dtFormatter)
    }
}