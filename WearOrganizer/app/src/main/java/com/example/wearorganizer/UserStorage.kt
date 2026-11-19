package com.example.wearorganizer

import android.content.Context
import com.google.gson.Gson

// Хранение пользователя в SharedPreferences в формате JSON
object UserStorage {
    private const val PREFS = "organizer_prefs"
    private const val KEY = "user"

    fun save(context: Context, user: User) {
        val json = Gson().toJson(user)
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY, json).apply()
    }

    fun load(context: Context): User? {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, null)
        if (json == null) {
            return null
        }
        return Gson().fromJson(json, User::class.java)
    }

    fun clear(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY).apply()
    }
}
