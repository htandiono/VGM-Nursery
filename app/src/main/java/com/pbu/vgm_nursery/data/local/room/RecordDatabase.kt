package com.pbu.vgm_nursery.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pbu.vgm_nursery.data.local.entity.RecordEntity

@Database(
    entities = [RecordEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class RecordDatabase : RoomDatabase() {

    abstract fun getRecordDao(): RecordDao
}