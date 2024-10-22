package com.pbu.vgm_nursery.ui.screen.record_list

import com.pbu.vgm_nursery.data.local.entity.RecordEntity

sealed class RecordListEvent {
    data class OnSaveCsvRecords(
        val records: List<RecordEntity>
    ) : RecordListEvent()
    object ResetState : RecordListEvent()
}
