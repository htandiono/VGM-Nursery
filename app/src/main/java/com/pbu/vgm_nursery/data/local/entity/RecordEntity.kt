package com.pbu.vgm_nursery.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "record_entity")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val operatorName: String = "",
    val SampleCode: String = "",
    val JlhDaun: Int = 0,
    val TinggiPokok: String = "",
    val PanjangDaun: String = "",
    val LebarDaun: String,
    val DiameterBatang: String = "",
    val Remarks: String = "",
    val tglSensus: String = "",
) : Parcelable