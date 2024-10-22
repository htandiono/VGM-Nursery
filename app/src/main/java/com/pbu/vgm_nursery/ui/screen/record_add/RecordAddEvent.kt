package com.pbu.vgm_nursery.ui.screen.record_add

sealed class RecordAddEvent {
    data class OnGetOperatorName(
        val operatorName: String,
    ) : RecordAddEvent()
    data class OnSampleCodeChange(
        val SampleCode: String,
    ) : RecordAddEvent()
    data class OnJlhDaunChange(
        val JlhDaun: String,
    ) : RecordAddEvent()
    data class OnTinggiPokokChange(
        val TinggiPokok: String,
    ) : RecordAddEvent()
    data class OnPanjangDaunChange(
        val PanjangDaun: String,
    ) : RecordAddEvent()
    data class OnLebarDaunChange(
        val LebarDaun: String,
    ) : RecordAddEvent()
    data class OnDiameterBatangChange(
        val DiameterBatang: String,
        ) : RecordAddEvent()
    data class OnRemarksChange(
        val Remarks: String,
    ) : RecordAddEvent()
    data class OnTglSensusChange(
        val tglSensus: String
    ) : RecordAddEvent()
    data class OnSaveClick(
        val recordAddState: RecordAddState
    ) : RecordAddEvent()
    object Reset : RecordAddEvent()
    object DeleteAllRecords : RecordAddEvent()
    object OnSaveClickWithTimestamp : RecordAddEvent()
}
