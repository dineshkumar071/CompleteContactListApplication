package com.intern.internproject.application_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.intern.internproject.respository.model.CLFollowers

@Dao
interface CLFollowersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFollowers(users: List<CLFollowers>)
}