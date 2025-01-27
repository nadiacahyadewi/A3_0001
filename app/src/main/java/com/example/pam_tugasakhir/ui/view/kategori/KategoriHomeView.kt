package com.example.pam_tugasakhir.ui.view.kategori


import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.model.Kategori
import com.example.pam_tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.pam_tugasakhir.ui.viewmodel.KategoriVM.HomeUiState
import com.example.pam_tugasakhir.ui.viewmodel.KategoriVM.KategoriHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel

object DestinasiKategoriHome : DestinasiNavigasi {
    override val route = "home kategori"
    override val titleRes = "Home Kategori"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKategoriScreen(
    navigateToKategoriEntry: () -> Unit,
    onBack: () -> Unit = {},
    onDetailClick: (Int) -> Unit,
    navigateToPemasok: () -> Unit,
    navigateToMerk: () -> Unit,
    viewModel: KategoriHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    var selectedTab by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Kategori Management",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "Manage Categories",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getKategori() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToKategoriEntry,
                shape = RoundedCornerShape(16.dp),
                containerColor = Color(0xFF6C5CE7),
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Category")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Kategori")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Product"
                        )
                    },
                    label = { Text("Produk") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        onBack()
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Categories"
                        )
                    },
                    label = { Text("Kategori") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Suppliers"
                        )
                    },
                    label = { Text("Pemasok") },
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        navigateToPemasok()
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Brands"
                        )
                    },
                    label = { Text("Merk") },
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                        navigateToMerk()
                    }
                )
            }
        },
        containerColor = Color(0xFFF5F6FF)
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.kategoriUiState,
            retryAction = { viewModel.getKategori() },
            onDeleteClick = { kategori ->
                viewModel.deleteKategori(kategori.idKategori)
                viewModel.getKategori()
            },
            onDetailClick = { kategori -> onDetailClick(kategori.idKategori) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit,
    onDetailClick: (Kategori) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.kategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data Kategori",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                KategoriLayout(
                    kategori = homeUiState.kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick,
                    onDetailClick = onDetailClick
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KategoriCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit,
    onDetailClick: (Kategori) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onDetailClick(kategori) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFF6C5CE7).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color(0xFF6C5CE7)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = kategori.deskripsiKategori,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = { onDeleteClick(kategori) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun KategoriLayout(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit,
    onDetailClick: (Kategori) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(kategori) { item ->
            KategoriCard(
                kategori = item,
                onDeleteClick = onDeleteClick,
                onDetailClick = onDetailClick
            )
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFF6C5CE7)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tidak ada data Kategori",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C5CE7)
            )
        ) {
            Text("Retry", color = Color.White)
        }
    }
}
