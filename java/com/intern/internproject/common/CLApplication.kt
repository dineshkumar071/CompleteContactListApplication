package com.intern.internproject.common

import android.app.Application
import android.content.Context
import com.intern.internproject.application_database.CLUserDatabase
import com.intern.internproject.respository.CLPreference

open class CLApplication : Application() {
    lateinit var mpreference: CLPreference


    companion object {
        @JvmStatic
        lateinit var instance: CLApplication
        @JvmStatic
        lateinit var dbInstance: CLUserDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        myPreference(this)
        dbInstance = CLUserDatabase.getInstance(this)
    }

    /**creating the preference object*/
    private fun myPreference(context: Context) {
        mpreference = CLPreference(context)
    }

    /**getter method for preference object*/
    fun getPrefer(): CLPreference {
        return mpreference
    }
}