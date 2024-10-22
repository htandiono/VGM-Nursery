package com.pbu.vgm_nursery.data.repository

import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import com.pbu.vgm_nursery.data.local.room.RecordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecordRepositoryImpl @Inject constructor(
    private val recordDao: RecordDao,
) : RecordRepository {

    override suspend fun insert(recordEntity: RecordEntity) {
        recordDao.insert(recordEntity)
    }

    override suspend fun update(recordEntity: RecordEntity) {
        recordDao.update(recordEntity)
    }

    override fun getAllRecords(): Flow<List<RecordEntity>> {
        return recordDao.getAllRecords()
    }

    override suspend fun delete(id: Int) {
        recordDao.delete(id)
    }

    override suspend fun deleteAll() {
        recordDao.deleteAll()
    }
}