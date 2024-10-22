package com.pbu.vgm_nursery.ui.screen.record_add

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
class RecordAddViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RecordAddState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            recordRepository.getAllRecords().collect { records ->
                _state.update {
                    it.copy(
                        isDatabaseEmpty = records.isEmpty()
                    )
                }
            }
        }
    }

    fun onEvent(event: RecordAddEvent) {
        when (event) {
            is RecordAddEvent.OnGetOperatorName -> _state.update {
                it.copy(
                    operatorName = event.operatorName
                )
            }
            is RecordAddEvent.OnTglSensusChange -> _state.update {
                it.copy(
                    tglSensus = event.tglSensus
                )
            }
            is RecordAddEvent.OnLebarDaunChange -> _state.update {
                it.copy(
                    LebarDaun = event.LebarDaun
                )
            }
            is RecordAddEvent.OnPanjangDaunChange -> _state.update {
                it.copy(
                    PanjangDaun = event.PanjangDaun
                )
            }
            is RecordAddEvent.OnDiameterBatangChange -> _state.update {
                it.copy(
                    DiameterBatang = event.DiameterBatang
                )
            }
            is RecordAddEvent.OnRemarksChange -> _state.update {
                it.copy(
                    Remarks = event.Remarks
                )
            }
            is RecordAddEvent.OnTinggiPokokChange -> _state.update {
                it.copy(
                    TinggiPokok = event.TinggiPokok
                )
            }
            is RecordAddEvent.OnJlhDaunChange -> _state.update {
                it.copy(
                    JlhDaun = event.JlhDaun
                )
            }
            is RecordAddEvent.OnSampleCodeChange -> _state.update {
                it.copy(
                    SampleCode = event.SampleCode
                )
            }
            is RecordAddEvent.OnSaveClick -> {
                saveRecord(event.recordAddState)
            }
            is RecordAddEvent.OnSaveClickWithTimestamp -> {
                saveRecordWithTimestamp()
            }
            is RecordAddEvent.Reset -> _state.update {
                RecordAddState()
            }

            RecordAddEvent.DeleteAllRecords -> deleteAllRecords()
        }
    }

    private fun deleteAllRecords() {
        viewModelScope.launch {
            recordRepository.deleteAll()
        }
    }

    private fun saveRecord(recordAddState: RecordAddState) {
        viewModelScope.launch {
            val recordEntity = recordAddState.toRecordEntity()
            recordRepository.insert(recordEntity)

            _state.update {
                it.copy(
                    isAddedToDatabase = true
                )
            }
        }
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun saveRecordWithTimestamp() {
        viewModelScope.launch {
            val currentTimestamp = getCurrentTimestamp()
            val recordAddStateWithTimestamp = state.value.copy(tglSensus = currentTimestamp)
            val recordEntity = recordAddStateWithTimestamp.toRecordEntity()
            recordRepository.insert(recordEntity)
            _state.update {
                it.copy(
                    isAddedToDatabase = true
                )
            }
        }
    }
}