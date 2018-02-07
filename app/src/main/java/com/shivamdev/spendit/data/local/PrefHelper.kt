package com.shivamdev.spendit.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Singleton
class PrefHelper @Inject constructor(private val preferences: SharedPreferences) {

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String = preferences.getString(key, "")

    fun clear() {
        preferences.edit().clear().apply()
    }
}