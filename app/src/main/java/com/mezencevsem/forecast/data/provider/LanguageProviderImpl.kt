package com.mezencevsem.forecast.data.provider

import android.content.Context
import com.mezencevsem.forecast.internal.Languages
import java.util.*

const val LANGUAGE = "LANGUAGE"
const val LANGUAGE_ENGLISH_CODE = "en"
const val LANGUAGE_RUSSIAN_CODE = "ru"

class LanguageProviderImpl(context: Context) : PreferenceProvider(context), LanguageProvider {

    override fun getLanguage(): Languages {
        val selectedLanguage = preferences.getString(LANGUAGE, Languages.ENGLISH.name)
        return Languages.valueOf(selectedLanguage!!)
    }

    override fun getLanguageCode(): String {
        val selectedLanguage = getLanguage()
        if (selectedLanguage == Languages.ENGLISH) return LANGUAGE_ENGLISH_CODE
        if (selectedLanguage == Languages.RUSSIAN) return LANGUAGE_RUSSIAN_CODE
        return getSystemLanguageCode()
    }

    // only en & ru
    private fun getSystemLanguageCode(): String {
        val systemLanguageCode = Locale.getDefault().language

        if (systemLanguageCode != LANGUAGE_ENGLISH_CODE
            && systemLanguageCode != LANGUAGE_RUSSIAN_CODE
        ) return LANGUAGE_ENGLISH_CODE

        return systemLanguageCode
    }
}