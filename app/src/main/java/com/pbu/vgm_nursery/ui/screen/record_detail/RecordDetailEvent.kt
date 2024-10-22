package com.pbu.vgm_nursery.ui.screen.record_detail

import com.pbu.vgm_nursery.data.local.entity.RecordEntity

sealed class RecordDetailEvent {
    data class OnGetRecordEntity(
        val recordEntity: RecordEntity,
    ) : RecordDetailEvent()
    data class OnSampleCodeChange(
        val SampleCode: String,
    ) : RecordDetailEvent()
    data class OnJlhDaunChange(
        val JlhDaun: String,
    ) : RecordDetailEvent()
    data class OnTinggiPokokChange(
        val TinggiPokok: String,
    ) : RecordDetailEvent()
    data class OnPanjangDaunChange(
        val PanjangDaun: String,
    ) : RecordDetailEvent()
    data class OnLebarDaunChange(
        val LebarDaun: String,
    ) : RecordDetailEvent()
    data class OnDiameterBatangChange(
        val DiameterBatang: String,
    ) : RecordDetailEvent()
    data class OnRemarksChange(
        val Remarks: String,
    ) : RecordDetailEvent()
    data class OnUpdateClick(
        val recordDetailState: RecordDetailState
    ) : RecordDetailEvent()
    data class OnDeleteClick(
        val recordId: Int
    ) : RecordDetailEvent()
    object OnUpdateClickWithTimestamp : RecordDetailEvent()
}
