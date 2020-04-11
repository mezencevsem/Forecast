package com.mezencevsem.forecast.data.provider

import android.content.Context
import com.mezencevsem.forecast.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"
const val UNIT_SYSTEM_METRIC_CODE = "m"
const val UNIT_SYSTEM_IMPERIAL_CODE = "f"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }

    override fun getUnitSystemCode(): String = if (getUnitSystem() == UnitSystem.METRIC)
        UNIT_SYSTEM_METRIC_CODE
        else UNIT_SYSTEM_IMPERIAL_CODE
}