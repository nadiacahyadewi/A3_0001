package com.example.pam_tugasakhir.ui.view.produk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.model.PilihMenu
import com.example.pam_tugasakhir.ui.costumwidget.CostumeTopAppBar
import com.example.pam_tugasakhir.ui.costumwidget.DynamicSelectedTextField
import com.example.pam_tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.pam_tugasakhir.ui.viewmodel.produk.InsertUiEvent
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.pam_tugasakhir.ui.viewmodel.produk.ProdukInsertVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "produk entry"
    override val titleRes = "Produk Entry"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryProdukScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProdukInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormInput(
                insertUiEvent = uiState.insertUiEvent,
                onValueChange = viewModel::updateInsertProdukState,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.insertProduk()
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
            value = insertUiEvent.namaProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProduk = it)) },
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Nama Produk") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsiProduk,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProduk = it)) },
            label = { Text("Deskripsi Produk") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Deskripsi Produk") },
            singleLine = false,
        )

        OutlinedTextField(
            value = insertUiEvent.harga,
            onValueChange = { onValueChange(insertUiEvent.copy(harga = it)) },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Harga") },
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.stok,
            onValueChange = { onValueChange(insertUiEvent.copy(stok = it)) },
            label = { Text("Stok") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = { Text("Masukkan Stok") },
            singleLine = true
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idKategori,
            options = PilihMenu.kategoriOption(),
            label = "Nama Kategori",
            onValueChangedEvent = { selectedKategori ->
                onValueChange(insertUiEvent.copy(idKategori = selectedKategori))
            }
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idMerk,
            options = PilihMenu.merkOption(),
            label = "Nama Merk",
            onValueChangedEvent = { selectedMerk ->
                onValueChange(insertUiEvent.copy(idMerk = selectedMerk))
            }
        )

        DynamicSelectedTextField(
            selectedValue = insertUiEvent.idPemasok,
            options = PilihMenu.pemasokOption(),
            label = "Nama Pemasok",
            onValueChangedEvent = { selectedPemasok ->
                onValueChange(insertUiEvent.copy(idPemasok = selectedPemasok))
            }
        )
    }
}
