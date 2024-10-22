package com.pbu.vgm_nursery.di

import android.content.Context
import androidx.room.Room
import com.pbu.vgm_nursery.data.local.room.RecordDao
import com.pbu.vgm_nursery.data.local.room.RecordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBrondolanDatabase(@ApplicationContext context: Context): RecordDatabase {
        return Room.databaseBuilder(
            context,
            RecordDatabase::class.java,
            "record"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRecordDao(recordDatabase: RecordDatabase): RecordDao {
        return recordDatabase.getRecordDao()
    }
}