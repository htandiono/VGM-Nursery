package com.pbu.vgm_nursery.di

import com.pbu.vgm_nursery.data.repository.RecordRepository
import com.pbu.vgm_nursery.data.repository.RecordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideRecordRepository(recordRepositoryImpl: RecordRepositoryImpl): RecordRepository
}