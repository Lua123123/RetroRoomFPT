package com.example.roomnek.databaseTopic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomnek.model.Success

@Dao
interface TopicDAO {

    @Insert
    fun insertTopic(success: Success)

    @Query("SELECT * FROM topics")
    fun getListTopic() : List<Success>
}