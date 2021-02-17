package com.intern.internproject.application_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.intern.internproject.respository.model.CLUserDetails

@Dao
interface CLUserDetailsDAO {
    @Insert(onConflict = REPLACE)
    fun insertUserDetails(user: CLUserDetails)

    @Query("select * from CLUserDetails")
    fun userDetail(): CLUserDetails

    @Query("DELETE FROM CLUserDetails")
    fun deleteUser()
}