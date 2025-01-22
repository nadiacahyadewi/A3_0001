package com.example.pam_tugasakhir.ui.view.Merk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.ui.costumwidget.CostumeTopAppBar
import com.example.pam_tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.InsertUiEvent
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.MerkInsertVM
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiMerkEntry : DestinasiNavigasi {
    override val route = "merk entry"
    override val titleRes = "Merk Entry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMerkScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MerkInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CostumeTopAppBar(
                title = DestinasiMerkEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormInput(
                insertUiEvent = uiState.insertUiEvent,
                onValueChange = viewModel::updateInsertMerkState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertMerk()
                        snackbarHostState.showSnackbar("Merk berhasil disimpan")
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(idMerk = it)) },
            label = { Text("ID Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan ID Merk") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.namaMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(namaMerk = it)) },
            label = { Text("Nama Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Merk") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiMerk,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiMerk = it)) },
            label = { Text("Deskripsi Merk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Merk") },
            singleLine = true
        )
    }
}
