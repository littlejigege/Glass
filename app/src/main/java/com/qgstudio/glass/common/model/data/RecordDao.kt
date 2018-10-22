package com.qgstudio.glass.common.model.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(records: List<Record>)

    @Query("SELECT * FROM records WHERE info = \"help\" ORDER BY warningTime DESC")
    fun getHelpsOrderByTime(): LiveData<List<Record>>

    @Query("SELECT * FROM records WHERE info = \"\" ORDER BY warningTime DESC")
    fun getNormalsOrderByTime(): LiveData<List<Record>>

    @Query("SELECT strftime('%Y-%m-%d',warningTime,'unixepoch') FROM records GROUP BY strftime('%Y-%m-%d',warningTime,'unixepoch') ORDER BY strftime('%Y-%m-%d',warningTime,'unixepoch')")
    fun getAllDays(): List<String>

    @Query("SELECT * FROM records WHERE strftime('%Y-%m-%d',warningTime,'unixepoch') = :dayString AND info = \"\" ORDER BY warningTime DESC")
    fun getNormalsByDay(dayString: String): List<Record>

}