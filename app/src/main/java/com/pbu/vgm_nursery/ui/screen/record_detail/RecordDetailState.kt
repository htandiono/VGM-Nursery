package com.pbu.vgm_nursery.ui.screen.record_detail

data class RecordDetailState(
    val recordId: Int = 0,
    val operatorName: String = "",
    val SampleCode: String = "",
    val JlhDaun: String = "",
    val TinggiPokok: String = "",
    val PanjangDaun: String = "",
    val LebarDaun: String = "",
    val DiameterBatang: String = "",
    val Remarks: String = "",
    val tglSensus: String = "",
    val isDeleteOrUpdateSuccess: Boolean = false,
)