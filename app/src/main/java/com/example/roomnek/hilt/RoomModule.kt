package com.example.roomnek.hilt

import android.content.Context
import androidx.room.Room
import com.example.roomnek.databaseTopic.TopicDAO
import com.example.roomnek.databaseTopic.TopicDatabaseBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "topics.db"
    private val instance: TopicDatabaseBase? = null

    @Provides
    fun provideTopicDatabase(@ApplicationContext context: Context) : TopicDatabaseBase {
        return Room.databaseBuilder(context, TopicDatabaseBase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun provideTopicDao(topicDatabaseBase: TopicDatabaseBase) : TopicDAO {
        return topicDatabaseBase.topicDAO()
    }
}