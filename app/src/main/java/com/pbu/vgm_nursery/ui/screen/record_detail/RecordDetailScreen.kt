package com.pbu.vgm_nursery.ui.screen.record_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import com.pbu.vgm_nursery.ui.navigation.Screen

var currentScanTarget = "SampleCode"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailScreen(
    scanResult: String?,
    navController: NavController,
    viewModel: RecordDetailViewModel = hiltViewModel(),
) {
    val recordEntity =
        navController.previousBackStackEntry?.savedStateHandle?.get<RecordEntity>("record")
    if (recordEntity != null) {
        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(RecordDetailEvent.OnGetRecordEntity(recordEntity))
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = scanResult) {
        if (!scanResult.isNullOrEmpty()) {
            when (com.pbu.vgm_nursery.ui.screen.record_add.currentScanTarget) {
                "SampleCode" -> viewModel.onEvent(RecordDetailEvent.OnSampleCodeChange(scanResult))
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Detail Record") })
        }
    ) { contentPadding ->
        RecordDetailContent(
            SampleCode = state.SampleCode,
            JlhDaun = state.JlhDaun,
            TinggiPokok = state.TinggiPokok,
            PanjangDaun = state.PanjangDaun,
            LebarDaun = state.LebarDaun,
            DiameterBatang = state.DiameterBatang,
            Remarks = state.Remarks,
            onSampleCodeChange = { viewModel.onEvent(RecordDetailEvent.OnSampleCodeChange(it)) },
            onJlhDaunChange = { viewModel.onEvent(RecordDetailEvent.OnJlhDaunChange(it)) },
            onTinggiPokokChange = { viewModel.onEvent(RecordDetailEvent.OnTinggiPokokChange(it)) },
            onPanjangDaunChange = { viewModel.onEvent(RecordDetailEvent.OnPanjangDaunChange(it)) },
            onLebarDaunChange = { viewModel.onEvent(RecordDetailEvent.OnLebarDaunChange(it)) },
            onDiameterBatangChange = { viewModel.onEvent(RecordDetailEvent.OnDiameterBatangChange(it)) },
            onRemarksChange = { viewModel.onEvent(RecordDetailEvent.OnRemarksChange(it)) },
            onScanClick = {
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("scan_result")
                navController.navigate(Screen.Scan.route)
            },
            onUpdateButtonClick = { viewModel.onEvent(RecordDetailEvent.OnUpdateClickWithTimestamp) },
            onDeleteButtonClick = { viewModel.onEvent(RecordDetailEvent.OnDeleteClick(state.recordId)) },
            modifier = Modifier.padding(contentPadding)
        )
    }

    if (state.isDeleteOrUpdateSuccess) {
        LaunchedEffect(key1 = Unit) {
            navController.navigateUp()
        }
    }
}

@Composable
fun RecordDetailContent(
    SampleCode: String,
    JlhDaun: String,
    TinggiPokok: String,
    PanjangDaun: String,
    LebarDaun: String,
    DiameterBatang: String,
    Remarks: String,
    onSampleCodeChange: (String) -> Unit,
    onJlhDaunChange: (String) -> Unit,
    onTinggiPokokChange: (String) -> Unit,
    onPanjangDaunChange: (String) -> Unit,
    onLebarDaunChange: (String) -> Unit,
    onDiameterBatangChange: (String) -> Unit,
    onRemarksChange: (String) -> Unit,
    onScanClick: () -> Unit,
    onUpdateButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDeleteAlertShow by remember { mutableStateOf(false) }

    if (isDeleteAlertShow) {
        AlertDialog(
            onDismissRequest = { isDeleteAlertShow = false },
            confirmButton = {
                TextButton(onClick = onDeleteButtonClick) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDeleteAlertShow = false }) {
                    Text(text = "No")
                }
            },
            title = { Text(text = "Delete Record") },
            text = { Text(text = "Anda yakin ingin menghapus record ini?") }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .padding(bottom = 144.dp) // add padding to make room for the Save button
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = SampleCode,
                    onValueChange = onSampleCodeChange,
                    placeholder = { Text(text = "Scan kode barcode") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        currentScanTarget = "SampleCode"
                        onScanClick()
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.height(56.dp)
                ) {
                    Text(text = "Scan")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = JlhDaun,
                onValueChange = { value ->
                    onJlhDaunChange(value.filter { it.isDigit() })
                },
                placeholder = { Text(text = "Jlh Daun di Pokok") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = TinggiPokok,
                onValueChange = onTinggiPokokChange,
                placeholder = { Text(text = "Tinggi Pokok (cm)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = PanjangDaun,
                onValueChange = onPanjangDaunChange,
                placeholder = { Text(text = "Panjang Daun (cm)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = LebarDaun,
                onValueChange = onLebarDaunChange,
                placeholder = { Text(text = "Lebar Daun (cm)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = DiameterBatang,
                onValueChange = onDiameterBatangChange,
                placeholder = { Text(text = "Diameter Batang (cm)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = Remarks,
                onValueChange = onRemarksChange,
                placeholder = { Text(text = "Remarks") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                enabled = SampleCode.isNotEmpty() && JlhDaun.isNotEmpty()
                        && TinggiPokok.isNotEmpty() && PanjangDaun.isNotEmpty()
                        && LebarDaun.isNotEmpty() && DiameterBatang.isNotEmpty(),
                onClick = onUpdateButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Update Record")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { isDeleteAlertShow = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Delete Record")
            }
        }
    }
}