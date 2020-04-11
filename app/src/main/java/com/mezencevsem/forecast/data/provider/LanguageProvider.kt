package com.mezencevsem.forecast.data.provider

import com.mezencevsem.forecast.internal.Languages

interface LanguageProvider {
    fun getLanguage(): Languages
    fun getLanguageCode(): String
}