package com.pbu.vgm_nursery.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recordEntity: RecordEntity)

    @Update
    suspend fun update(recordEntity: RecordEntity)

    @Query("SELECT * FROM record_entity ORDER BY id DESC")
    fun getAllRecords(): Flow<List<RecordEntity>>

    @Query("DELETE FROM record_entity WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM record_entity")
    suspend fun deleteAll()
}