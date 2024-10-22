package com.pbu.vgm_nursery.util

import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import com.pbu.vgm_nursery.ui.screen.record_add.RecordAddState
import com.pbu.vgm_nursery.ui.screen.record_detail.RecordDetailState

fun RecordAddState.toRecordEntity(): RecordEntity {
    return RecordEntity(
        operatorName = operatorName,
        SampleCode = SampleCode,
        JlhDaun = JlhDaun.toInt(),
        TinggiPokok = TinggiPokok,
        PanjangDaun = PanjangDaun,
        LebarDaun = LebarDaun,
        DiameterBatang = DiameterBatang,
        Remarks = Remarks,
        tglSensus = tglSensus
    )
}

fun RecordDetailState.toRecordEntity(): RecordEntity {
    return RecordEntity(
        id = recordId,
        operatorName = operatorName,
        SampleCode = SampleCode,
        JlhDaun = JlhDaun.toInt(),
        TinggiPokok = TinggiPokok,
        PanjangDaun = PanjangDaun,
        LebarDaun = LebarDaun,
        DiameterBatang = DiameterBatang,
        Remarks = Remarks,
        tglSensus = tglSensus
    )
}