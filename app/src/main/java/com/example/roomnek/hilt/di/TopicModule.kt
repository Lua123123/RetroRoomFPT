package com.example.roomnek.hilt.di

import com.example.roomnek.databaseTopic.TopicDAO
import com.example.roomnek.hilt.Repository
import com.example.roomnek.hilt.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TopicModule {

    @Provides
    fun provideTopicRepository(topicDAO: TopicDAO) : Repository {
        return RepositoryImpl(topicDAO)
    }
}