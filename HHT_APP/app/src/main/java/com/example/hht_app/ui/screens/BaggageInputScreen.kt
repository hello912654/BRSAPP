package com.example.hht_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hht_app.data.model.BSM
import com.example.hht_app.ui.viewmodel.BaggageViewModel

@Composable
fun BaggageInputScreen(
    modifier: Modifier = Modifier,
    viewModel: BaggageViewModel = hiltViewModel()
) {
    var baggageTag by remember { mutableStateOf("") }

    // get all Bsm list by viewModel.bsmList.collectAsState()
    val bsmList by viewModel.bsmList.collectAsState()
    val processResult by viewModel.processResult.collectAsState()

//    // dev only
//    val bsmList = remember {
//        listOf(
//            BSM("1234567890123", "CI100", "TPE", "John Doe"),
//            BSM("2234567890123", "CI200", "HKG", "Jane Smith"),
//            BSM("3234567890123", "CI300", "NRT", "Mary Johnson")
//        )
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // title
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "行李處理系統",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "請輸入或掃描行李條碼",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // input area
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = baggageTag,
                    onValueChange = { baggageTag = it },
                    label = { Text("行李條碼") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        if (baggageTag.isNotBlank()) {
                            viewModel.processBaggage(baggageTag)
                            baggageTag = "" // 清空輸入
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("確認")
                }
            }
        }

        // do process result
        processResult?.let { result ->
            when (result) {
                is BaggageViewModel.ProcessResult.Success -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "處理成功",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                IconButton(onClick = { viewModel.clearProcessResult()  }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "關閉"
                                    )
                                }
                            }
                            Text("行李條碼: ${result.bsm.bagTagNumber}")
//                    Text("航班: ${result.bsm.flightNumber}")
//                    Text("目的地: ${result.bsm.destination}")
                        }
                    }
                }
                is BaggageViewModel.ProcessResult.Error -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "處理失敗",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                IconButton(onClick = { viewModel.clearProcessResult() }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "關閉"
                                    )
                                }
                            }
                            Text(result.message)
                        }
                    }
                }
            }
        }

        // show count of processed and unprocessed BSM
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = bsmList.size.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "總數量",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                VerticalDivider()
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = bsmList.count { it.isProcessed }.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = "已處理",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                VerticalDivider()
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = bsmList.count { !it.isProcessed }.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "未處理",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // BSM list
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "待處理行李",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    items(bsmList.filterNot { it.isProcessed }) { bsm ->
                        BSMItem(bsm = bsm)
                    }
                }
            }
        }

    }

}

@Composable
fun BSMItem(bsm: BSM) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "行李條碼: ${bsm.bagTagNumber}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
//            Text(
//                text = "航班: ${bsm.flightNumber} | 目的地: ${bsm.destination}",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//            Text(
//                text = "旅客: ${bsm.passengerName}",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
        }
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(24.dp)
            .width(1.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )
}

@Preview(showBackground = true)
@Composable
fun BaggageInputScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BaggageInputScreen()
        }
    }
}