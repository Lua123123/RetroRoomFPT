package com.example.roomnek.hilt

import com.example.roomnek.databaseTopic.TopicDAO
import com.example.roomnek.model.Success

class RepositoryImpl (private val topicDAO: TopicDAO): Repository {
    override fun insertTopic(success: Success) {
        topicDAO.insertTopic(success)
    }

    override fun getListTopic(): List<Success> {
        return topicDAO.getListTopic()
    }
}