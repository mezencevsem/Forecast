package com.mezencevsem.forecast.data.provider

import com.mezencevsem.forecast.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
    fun getUnitSystemCode(): String
}