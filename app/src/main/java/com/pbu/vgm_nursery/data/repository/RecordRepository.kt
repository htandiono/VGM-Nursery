package com.pbu.vgm_nursery.data.repository

import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

interface RecordRepository {

    suspend fun insert(recordEntity: RecordEntity)

    suspend fun update(recordEntity: RecordEntity)

    fun getAllRecords(): Flow<List<RecordEntity>>

    suspend fun delete(id: Int)

    suspend fun deleteAll()
}