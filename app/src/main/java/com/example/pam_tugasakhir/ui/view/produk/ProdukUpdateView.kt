package com.example.pam_tugasakhir.ui.view.produk


import androidx.compose.foundation.layout.*
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
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.pam_tugasakhir.ui.viewmodel.UpdateProdukVM
import com.example.pam_tugasakhir.ui.viewmodel.UpdateUiEvent
import com.example.pam_tugasakhir.ui.viewmodel.UpdateUiState

object DestinasiUpdateProduk : DestinasiNavigasi {
    override val route = "update produk"
    override val titleRes = "Update Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenProduk(
    idProduk: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateProdukVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    // Load data once when the screen opens
    LaunchedEffect(idProduk) {
        viewModel.loadProdukData(idProduk)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateProduk.titleRes,
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
                is UpdateUiState.Loading -> CircularProgressIndicator()
                is UpdateUiState.Success -> {
                    val produk = (uiState as UpdateUiState.Success).produk
                    UpdateForm(
                        idProduk = produk.idProduk,
                        namaProduk = produk.namaProduk,
                        deskripsiProduk = produk.deskripsiProduk,
                        harga = produk.harga,
                        stok = produk.stok,
                        idKategori = produk.idKategori,
                        idPemasok = produk.idPemasok,
                        idMerk = produk.idMerk,
                        onUpdateClick = {
                            viewModel.updateUiState(it)
                            viewModel.updateProduk()
                            onNavigateBack()
                        }
                    )
                }
                is UpdateUiState.Error -> {
                    Text("Error: ${(uiState as UpdateUiState.Error).message}")
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateForm(
    idProduk: String,
    namaProduk: String,
    deskripsiProduk: String,
    harga: String,
    stok: String,
    idKategori: String,
    idPemasok: String,
    idMerk: String,
    onUpdateClick: (UpdateUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var namaProdukState by remember { mutableStateOf(namaProduk) }
    var deskripsiProdukState by remember { mutableStateOf(deskripsiProduk) }
    var hargaState by remember { mutableStateOf(harga) }
    var stokState by remember { mutableStateOf(stok) }
    var idKategoriState by remember { mutableStateOf(idKategori) }
    var idPemasokState by remember { mutableStateOf(idPemasok) }
    var idMerkState by remember { mutableStateOf(idMerk) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaProdukState,
            onValueChange = { namaProdukState = it },
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = deskripsiProdukState,
            onValueChange = { deskripsiProdukState = it },
            label = { Text("Deskripsi Produk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        OutlinedTextField(
            value = hargaState,
            onValueChange = { hargaState = it },
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        OutlinedTextField(
            value = stokState,
            onValueChange = { stokState = it },
            label = { Text("Stok") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        OutlinedTextField(
            value = idKategoriState,
            onValueChange = { idKategoriState = it },
            label = { Text("ID Kategori") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = idPemasokState,
            onValueChange = { idPemasokState = it },
            label = { Text("ID Pemasok") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = idMerkState,
            onValueChange = { idMerkState = it },
            label = { Text("ID Merk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                onUpdateClick(
                    UpdateUiEvent(
                        idProduk = idProduk,
                        namaProduk = namaProdukState,
                        deskripsiProduk = deskripsiProdukState,
                        harga = hargaState,
                        stok = stokState,
                        idKategori = idKategoriState,
                        idPemasok = idPemasokState,
                        idMerk = idMerkState
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}