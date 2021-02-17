package com.intern.internproject.application_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.CLLoginResponseUser
import com.intern.internproject.respository.model.CLFollowers
import com.intern.internproject.respository.model.CLUserDetails

@Database(
    entities = [CLLoginResponseUser::class, CLUserDetails::class, CLFollowers::class],
    version = 1
)
abstract class CLUserDatabase : RoomDatabase() {
    abstract fun userDOA(): CLUserDAO
    abstract fun userDetailsDOA(): CLUserDetailsDAO
    abstract fun followersDAO(): CLFollowersDAO

    companion object {
        private lateinit var instance1: CLUserDatabase
        fun getInstance(context: Context?): CLUserDatabase {
            if (!::instance1.isInitialized && context != null) {
                synchronized(CLUserDatabase::class.java) {
                    instance1 =
                        Room.databaseBuilder(context, CLUserDatabase::class.java, "users_database")
                            .build()
                }
            }
            return instance1
        }
    }
}