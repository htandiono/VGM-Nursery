package com.pbu.vgm_nursery.ui.screen.record_add

data class RecordAddState(
    val operatorName: String = "",
    val SampleCode: String = "",
    val JlhDaun: String = "",
    val TinggiPokok: String = "",
    val PanjangDaun: String = "",
    val LebarDaun: String = "",
    val DiameterBatang: String = "",
    val Remarks: String = "",
    val tglSensus: String = "",
    val isAddedToDatabase: Boolean = false,
    val isDatabaseEmpty: Boolean = false,
)