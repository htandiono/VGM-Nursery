package com.pbu.vgm_nursery.ui.screen.record_list

import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pbu.vgm_nursery.data.local.entity.RecordEntity
import com.pbu.vgm_nursery.ui.components.RecordItem
import com.pbu.vgm_nursery.ui.navigation.Screen
import com.pbu.vgm_nursery.util.createCustomFile
import com.pbu.vgm_nursery.util.toFile
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.pbu.vgm_nursery.R
import kotlinx.coroutines.launch

@Composable
fun RecordListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RecordListViewModel = hiltViewModel(),
    getCsvFileImmediately: Boolean = false,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isGetCsvFile by rememberSaveable { mutableStateOf(getCsvFileImmediately) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            isGetCsvFile = false
            scope.launch {
                try {
                    if (uri != null) {
                        val csvFile = uri.toFile(context)
                        csvReader().openAsync(csvFile) {
                            val records = readAllAsSequence().map {
                                RecordEntity(
                                    id = it[0].toInt(),
                                    SampleCode = it[1],
                                    JlhDaun = it[2].toInt(),
                                    TinggiPokok = it[3],
                                    PanjangDaun = it[4],
                                    LebarDaun = it[5],
                                    DiameterBatang = it[6],
                                    Remarks = it[7],
                                    operatorName = it[8],
                                    tglSensus = it[9],
                                )
                            }
                            viewModel.onEvent(RecordListEvent.OnSaveCsvRecords(records.toList()))
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "File format is not valid", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    LaunchedEffect(key1 = isGetCsvFile) {
        if (isGetCsvFile) {
            launcher.launch(arrayOf("text/comma-separated-values"))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(items = state.records) {
                RecordItem(
                    SampleCode = it.SampleCode,
                    JlhDaun = it.JlhDaun.toString(),
                    TinggiPokok = it.TinggiPokok,
                    PanjangDaun = it.PanjangDaun,
                    LebarDaun = it.LebarDaun,
                    DiameterBatang = it.DiameterBatang,
                    Remarks = it.Remarks,
                    onItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("record", it)
                        navController.navigate(Screen.RecordDetail.route)
                    }
                )
                HorizontalDivider()
            }
        }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Button(
                enabled = state.records.isNotEmpty(),
                onClick = {
                    scope.launch {
                        val data = state.records.map {
                            listOf(
                                it.id,
                                it.SampleCode,
                                "${it.JlhDaun}",
                                it.TinggiPokok,
                                it.PanjangDaun,
                                it.LebarDaun,
                                it.DiameterBatang,
                                it.Remarks,
                                it.operatorName,
                                it.tglSensus
                            )
                        }
                        csvWriter().writeAllAsync(
                            data,
                            createCustomFile(context) ?: return@launch
                        )
                        Toast.makeText(
                            context,
                            "Saved in " + Environment.DIRECTORY_DOCUMENTS + context.getString(
                                R.string.filepath
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Export To CSV File")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    launcher.launch(arrayOf("text/comma-separated-values"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(imageVector = Icons.Outlined.FileOpen, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Load From CSV File")
            }
        }
    }
}