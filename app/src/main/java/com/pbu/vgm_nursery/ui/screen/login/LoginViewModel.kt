package com.pbu.vgm_nursery.ui.screen.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbu.vgm_nursery.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val recordRepository: RecordRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        getOperatorName()
    }

    private fun getOperatorName() {
        _state.update {
            it.copy(
                operatorName = prefs.getString("operator_name", "") ?: ""
            )
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnOperatorNameChange -> {
                _state.update {
                    it.copy(
                        operatorName = event.operatorName
                    )
                }
            }
            is LoginEvent.OnOperatorNameSaved -> saveOperatorName(event.operatorName)
            is LoginEvent.OnDeleteAllRecords -> deleteAllRecords()
            is LoginEvent.DismissAlert -> _state.update {
                it.copy(
                    isAlertShow = false
                )
            }
            is LoginEvent.ResetState -> {
                _state.update {
                    LoginState()
                }
                getOperatorName()
            }
            is LoginEvent.GetAlertStatus -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isAlertShow = recordRepository.getAllRecords().first().isNotEmpty()
                        )
                    }
                }
            }
        }
    }

    private fun deleteAllRecords() {
        viewModelScope.launch {
            recordRepository.deleteAll()
        }
    }

    private fun saveOperatorName(operatorName: String) {
        prefs.edit {
            putString("operator_name", operatorName)
            commit()
        }
    }

}