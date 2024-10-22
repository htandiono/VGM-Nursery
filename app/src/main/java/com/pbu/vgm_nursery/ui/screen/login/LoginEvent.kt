package com.pbu.vgm_nursery.ui.screen.login

sealed class LoginEvent {
    data class OnOperatorNameChange(
        val operatorName: String
    ) : LoginEvent()
    data class OnOperatorNameSaved(
        val operatorName: String
    ) : LoginEvent()
    object OnDeleteAllRecords : LoginEvent()
    object DismissAlert : LoginEvent()
    object ResetState : LoginEvent()
    object GetAlertStatus : LoginEvent()
}
