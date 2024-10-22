package com.pbu.vgm_nursery.ui.screen.record_add

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pbu.vgm_nursery.R
import com.pbu.vgm_nursery.ui.navigation.Screen
import com.pbu.vgm_nursery.ui.screen.record_list.RecordListScreen
import kotlinx.coroutines.launch

var currentScanTarget = "SampleCode"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RecordAddScreen(
    scanResult: String?,
    operatorName: String,
    navController: NavController,
    viewModel: RecordAddViewModel = hiltViewModel(),
    selectedTabIndexDef: Int = 0,
    getCsvFileImmediately: Boolean = false,
) {
    LaunchedEffect(key1 = operatorName) {
        viewModel.onEvent(RecordAddEvent.OnGetOperatorName(operatorName))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = scanResult) {
        if (!scanResult.isNullOrEmpty()) {
            when (currentScanTarget) {
                "SampleCode" -> viewModel.onEvent(RecordAddEvent.OnSampleCodeChange(scanResult))
            }
        }
    }
    val tabItems = listOf("New Record", "Record List")
    val pagerState = rememberPagerState {
        tabItems.size
    }
    if (selectedTabIndexDef == 1) {
        LaunchedEffect(key1 = Unit) {
            pagerState.animateScrollToPage(1)
        }
    }
    val scope = rememberCoroutineScope()
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }
    var showDeleteAlert by remember { mutableStateOf(false) }

    if (showDeleteAlert) {
        AlertDialog(
            onDismissRequest = { showDeleteAlert = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(RecordAddEvent.DeleteAllRecords)
                        showDeleteAlert = false
                    }
                ) {
                    Text(text = "Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAlert = false }) {
                    Text(text = "Tidak")
                }
            },
            title = { Text(text = "Hapus File Record") },
            text = { Text(text = "Apakah anda yakin untuk menghapus semua records yang tampil?") }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    if (selectedTabIndex == 1 && !state.isDatabaseEmpty) {
                        IconButton(onClick = { showDeleteAlert = true }) {
                            Icon(
                                imageVector = Icons.Rounded.DeleteForever,
                                contentDescription = null,
                            )
                        }
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = item) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> {
                        RecordAddContent(
                            SampleCode = state.SampleCode,
                            JlhDaun = state.JlhDaun,
                            TinggiPokok = state.TinggiPokok,
                            PanjangDaun = state.PanjangDaun,
                            LebarDaun = state.LebarDaun,
                            DiameterBatang = state.DiameterBatang,
                            Remarks = state.Remarks,
                            onSampleCodeChange = {
                                viewModel.onEvent(
                                    RecordAddEvent.OnSampleCodeChange(
                                        it
                                    )
                                )
                            },
                            onJlhDaunChange = { viewModel.onEvent(RecordAddEvent.OnJlhDaunChange(it)) },
                            onTinggiPokokChange = { viewModel.onEvent(RecordAddEvent.OnTinggiPokokChange(it)) },
                            onPanjangDaunChange = { viewModel.onEvent(RecordAddEvent.OnPanjangDaunChange(it)) },
                            onLebarDaunChange = { viewModel.onEvent(RecordAddEvent.OnLebarDaunChange(it)) },
                            onDiameterBatangChange = { viewModel.onEvent(RecordAddEvent.OnDiameterBatangChange(it)) },
                            onRemarksChange = { viewModel.onEvent(RecordAddEvent.OnRemarksChange(it)) },
                            onScanClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("scan_result")
                                navController.navigate(Screen.Scan.route)
                            },
                            onSaveButtonClick = { viewModel.onEvent(RecordAddEvent.OnSaveClickWithTimestamp) },
                        )
                    }

                    1 -> {
                        RecordListScreen(navController = navController, getCsvFileImmediately = getCsvFileImmediately)
                    }
                }
            }
        }
    }

    if (state.isAddedToDatabase) {
        LaunchedEffect(key1 = Unit) {
            viewModel.onEvent(RecordAddEvent.Reset)
        }
    }
}

@Composable
fun RecordAddContent(
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
    onSaveButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp) // add padding to make room for the Save button
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
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                enabled = SampleCode.isNotEmpty() && JlhDaun.isNotEmpty()
                        && TinggiPokok.isNotEmpty() && PanjangDaun.isNotEmpty()
                        && LebarDaun.isNotEmpty() && DiameterBatang.isNotEmpty(),
                onClick = onSaveButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}