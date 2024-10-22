package com.pbu.vgm_nursery.ui.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pbu.vgm_nursery.R
import com.pbu.vgm_nursery.ui.navigation.Screen
import com.pbu.vgm_nursery.ui.theme.SPULab01ReceivingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    if (state.isAlertShow) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(LoginEvent.DismissAlert) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(LoginEvent.OnDeleteAllRecords)
                        navController.navigate(Screen.RecordAdd.createRoute(state.operatorName))
                        viewModel.onEvent(LoginEvent.ResetState)
                    }
                ) {
                    Text(text = "Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.onEvent(LoginEvent.DismissAlert)
                    navController.navigate(Screen.RecordAdd.createRoute(state.operatorName))
                }) {
                    Text(text = "Tidak")
                }
            },
            title = { Text(text = "Hapus File Record") },
            text = { Text(text = "File hari ini sudah ada, apakah anda yakin untuk menghapus data?") }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        }
    ) { contentPadding ->
        LoginContent(
            modifier = Modifier.padding(contentPadding),
            operatorName = state.operatorName,
            onOperatorNameChange = {
                viewModel.onEvent(LoginEvent.OnOperatorNameChange(it))
            },
            onButtonNewFileClick = {
                viewModel.onEvent(LoginEvent.OnOperatorNameSaved(state.operatorName))
                viewModel.onEvent(LoginEvent.GetAlertStatus)
                scope.launch {
                    delay(0.5.seconds)
                    if (!state.isAlertShow) {
                        navController.navigate(Screen.RecordAdd.createRoute(state.operatorName))
                    }
                }
            },
            onButtonLoadFileClick = {
                viewModel.onEvent(LoginEvent.OnOperatorNameSaved(state.operatorName))
                scope.launch {
                    delay(0.5.seconds)
                    navController.navigate(
                        Screen.RecordAdd.createRoute(
                            state.operatorName,
                            1,
                            true
                        )
                    )
                }
            },
        )
    }
}

@Composable
fun LoginContent(
    operatorName: String,
    onOperatorNameChange: (String) -> Unit,
    onButtonNewFileClick: () -> Unit,
    onButtonLoadFileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = operatorName,
            onValueChange = onOperatorNameChange,
            placeholder = { Text(text = "Nama Operator") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 112.dp)
                .align(Alignment.Center)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                enabled = operatorName.isNotEmpty(),
                onClick = onButtonNewFileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "New File")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = operatorName.isNotEmpty(),
                onClick = onButtonLoadFileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Load File")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    SPULab01ReceivingTheme {
        LoginContent(
            operatorName = "",
            onOperatorNameChange = {},
            onButtonNewFileClick = { /*TODO*/ },
            onButtonLoadFileClick = { /*TODO*/ },
        )
    }
}