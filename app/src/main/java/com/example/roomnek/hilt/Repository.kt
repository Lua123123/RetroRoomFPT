package com.example.roomnek.hilt

import com.example.roomnek.model.Success

interface Repository {
    fun insertTopic(success: Success)
    fun getListTopic() : List<Success>
}