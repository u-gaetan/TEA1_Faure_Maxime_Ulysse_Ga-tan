package com.example.tea1

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataPersistence {
    private const val PREFS_NAME = "TeaPrefs"
    private const val KEY_PROFILES = "profiles"

    fun saveProfiles(context: Context, profiles: List<ProfileToDo>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(profiles)
        prefs.edit().putString(KEY_PROFILES, json).apply()
    }

    fun loadProfiles(context: Context): MutableList<ProfileToDo> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_PROFILES, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<ProfileToDo>>() {}.type
        return Gson().fromJson(json, type)
    }
}