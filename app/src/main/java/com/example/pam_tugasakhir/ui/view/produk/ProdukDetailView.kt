package com.example.pam_tugasakhir.ui.view.produk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.model.Produk
import com.example.pam_tugasakhir.ui.costumwidget.CostumeTopAppBar
import com.example.pam_tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.pam_tugasakhir.ui.viewmodel.produk.DetailProdukUiState
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.pam_tugasakhir.ui.viewmodel.produk.ProdukDetailVM

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail_produk"
    override val titleRes = "Detail Produk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    idProduk: String,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit,
    viewModel: ProdukDetailVM = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idProduk) {
        viewModel.getProdukById(idProduk)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Produk")
            }
        },
    ) { innerPadding ->
        when (val state = uiState.value) {
            is DetailProdukUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
            is DetailProdukUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailProdukCard(produk = state.produk, onEditClick = onEditClick)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            is DetailProdukUiState.Error -> OnError(retryAction = { viewModel.getProdukById(idProduk) })
        }
    }
}

@Composable
fun DetailProdukCard(produk: Produk, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Nama Produk: ${produk.namaProduk}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "ID Produk: ${produk.idProduk}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Harga: Rp.${produk.harga}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Stok: ${produk.stok}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Deskripsi: ${produk.deskripsiProduk}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "ID Kategori: ${produk.idKategori}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "ID Pemasok: ${produk.idPemasok}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "ID Merk: ${produk.idMerk}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Produk")
                }
            }
        }
    }
}

