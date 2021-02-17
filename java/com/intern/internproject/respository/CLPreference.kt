package com.intern.internproject.respository

import android.content.Context
import android.content.SharedPreferences


class CLPreference(context: Context) {
    private val SHARED_PREF_NAME = "USER"
    var sharedpreferences: SharedPreferences?

    init {
        this.sharedpreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    var editor = sharedpreferences?.edit()
    var listOfUsers: String?
        get() {
            val users: String? = sharedpreferences?.getString(SHARED_PREF_NAME, "")
            return users
        }
        set(value) {
            val prefsEditor = editor
            prefsEditor?.putString(SHARED_PREF_NAME, value)?.apply()
        }

    fun clearSharedPreference() {
        val prefsEditor = editor
        prefsEditor?.clear()?.apply()
    }
}