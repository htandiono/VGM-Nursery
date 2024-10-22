package com.pbu.vgm_nursery.ui.screen.record_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbu.vgm_nursery.data.repository.RecordRepository
import com.pbu.vgm_nursery.util.toRecordEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RecordDetailState())
    val state = _state.asStateFlow()

    fun onEvent(event: RecordDetailEvent) {
        when (event) {
            is RecordDetailEvent.OnGetRecordEntity -> _state.update {
                it.copy(
                    recordId = event.recordEntity.id,
                    operatorName = event.recordEntity.operatorName,
                    SampleCode = event.recordEntity.SampleCode,
                    JlhDaun = event.recordEntity.JlhDaun.toString(),
                    TinggiPokok = event.recordEntity.TinggiPokok,
                    PanjangDaun = event.recordEntity.PanjangDaun,
                    LebarDaun = event.recordEntity.LebarDaun,
                    DiameterBatang = event.recordEntity.DiameterBatang,
                    Remarks = event.recordEntity.Remarks,
                    tglSensus = event.recordEntity.tglSensus
                )
            }

            is RecordDetailEvent.OnTinggiPokokChange -> _state.update {
                it.copy(
                    TinggiPokok = event.TinggiPokok
                )
            }

            is RecordDetailEvent.OnPanjangDaunChange -> _state.update {
                it.copy(
                    PanjangDaun = event.PanjangDaun
                )
            }

            is RecordDetailEvent.OnLebarDaunChange -> _state.update {
                it.copy(
                    LebarDaun = event.LebarDaun
                )
            }

            is RecordDetailEvent.OnDiameterBatangChange -> _state.update {
                it.copy(
                    DiameterBatang = event.DiameterBatang
                )
            }

            is RecordDetailEvent.OnRemarksChange -> _state.update {
                it.copy(
                    Remarks = event.Remarks
                )
            }

            is RecordDetailEvent.OnJlhDaunChange -> _state.update {
                it.copy(
                    JlhDaun = event.JlhDaun
                )
            }

            is RecordDetailEvent.OnSampleCodeChange -> _state.update {
                it.copy(
                    SampleCode = event.SampleCode
                )
            }

            is RecordDetailEvent.OnUpdateClick -> updateRecord(event.recordDetailState)
            is RecordDetailEvent.OnUpdateClickWithTimestamp -> {
                updateRecordWithTimestamp()
            }

            is RecordDetailEvent.OnDeleteClick -> deleteRecord(event.recordId)
        }
    }

    private fun deleteRecord(recordInt: Int) {
        viewModelScope.launch {
            recordRepository.delete(recordInt)

            _state.update {
                it.copy(
                    isDeleteOrUpdateSuccess = true
                )
            }
        }
    }

    private fun updateRecord(recordDetailState: RecordDetailState) {
        viewModelScope.launch {
            val recordEntity = recordDetailState.toRecordEntity()
            recordRepository.update(recordEntity)

            _state.update {
                it.copy(
                    isDeleteOrUpdateSuccess = true
                )
            }
        }
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun updateRecordWithTimestamp() {
        viewModelScope.launch {
            val currentTimestamp = getCurrentTimestamp()
            val recordDetailStateWithTimestamp = state.value.copy(tglSensus = currentTimestamp)
            val recordEntity = recordDetailStateWithTimestamp.toRecordEntity()
            recordRepository.update(recordEntity)
            _state.update {
                it.copy(
                    isDeleteOrUpdateSuccess = true
                )
            }
        }
    }
}