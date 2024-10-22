package com.pbu.vgm_nursery.ui.screen.record_list

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import com.pbu.vgm_nursery.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordListViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val prefs: SharedPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow(RecordListState())
    val state = _state.asStateFlow()

    init {
        getAllRecords()
        getOperatorName()
    }

    fun onEvent(event: RecordListEvent) {
        when (event) {
            is RecordListEvent.OnSaveCsvRecords -> saveCsvRecords(event.records)
            is RecordListEvent.ResetState -> {
                _state.update {
                    RecordListState()
                }
                getAllRecords()
                getOperatorName()
            }
        }
    }

    private fun saveCsvRecords(records: List<RecordEntity>) {
        viewModelScope.launch {
            records.forEach { record ->
                recordRepository.insert(record)
            }
        }
    }

    private fun getOperatorName() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    operatorName = prefs.getString("operator_name", "") ?: ""
                )
            }
        }
    }

    private fun getAllRecords() {
        viewModelScope.launch {
            recordRepository.getAllRecords().collect { records ->
                _state.update {
                    it.copy(
                        records = records
                    )
                }
            }
        }
    }
}