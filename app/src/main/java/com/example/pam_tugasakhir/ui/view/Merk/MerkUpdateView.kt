package com.example.pam_tugasakhir.ui.view.Merk

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.ui.costumwidget.CostumeTopAppBar
import com.example.pam_tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.MerkUpdateVM
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.UpdateMerkEvent
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.UpdateMerkUiState
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel

object DestinasiUpdateMerk : DestinasiNavigasi {
    override val route = "update merk"
    override val titleRes = "Update Merk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenMerk(
    idMerk: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MerkUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idMerk) {
        viewModel.loadMerkData(idMerk)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateMerk.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is UpdateMerkUiState.Loading -> CircularProgressIndicator()
                is UpdateMerkUiState.Success -> {
                    val merk = (uiState as UpdateMerkUiState.Success).merk
                    UpdateFormMerk(
                        idMerk = merk.idMerk,
                        namaMerk = merk.namaMerk,
                        deskripsiMerk = merk.deskripsiMerk,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateMerk()
                            onNavigateBack()
                        }
                    )
                }
                is UpdateMerkUiState.Error -> {
                    Text("Error: ${(uiState as UpdateMerkUiState.Error).message}")
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFormMerk(
    idMerk: String,
    namaMerk: String,
    deskripsiMerk: String,
    onUpdateClick: (UpdateMerkEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var namaMerkState by remember { mutableStateOf(namaMerk) }
    var deskripsiMerkState by remember { mutableStateOf(deskripsiMerk) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaMerkState,
            onValueChange = { namaMerkState = it },
            label = { Text("Nama Merk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsiMerkState,
            onValueChange = { deskripsiMerkState = it },
            label = { Text("Deskripsi Merk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateMerkEvent(
                        idMerk = idMerk,
                        namaMerk = namaMerkState,
                        deskripsiMerk = deskripsiMerkState
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
