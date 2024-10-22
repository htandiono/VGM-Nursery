package com.pbu.vgm_nursery.ui.screen.record_list

import com.pbu.vgm_nursery.data.local.entity.RecordEntity

data class RecordListState(
    val operatorName: String = "",
    val records: List<RecordEntity> = emptyList(),
)