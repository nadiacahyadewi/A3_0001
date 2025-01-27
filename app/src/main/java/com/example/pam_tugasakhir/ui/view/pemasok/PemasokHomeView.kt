package com.example.pam_tugasakhir.ui.view.pemasok

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.R
import com.example.pam_tugasakhir.model.Pemasok
import com.example.pam_tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.HomeUiState
import com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.PemasokHomeVM

object DestinasiPemasokHome : DestinasiNavigasi {
    override val route = "home pemasok"
    override val titleRes = "Home Pemasok"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePemasokScreen(
    navigateToPemasokEntry: () -> Unit,
    navigateToMerk: () -> Unit,
    navigateToKategori: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: PemasokHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    var selectedTab by remember { mutableStateOf(2) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Supplier Management",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "Manage Suppliers",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getPemasok() }) {
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
                onClick = navigateToPemasokEntry,
                shape = RoundedCornerShape(16.dp),
                containerColor = Color(0xFF6C5CE7),
                contentColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Supplier")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Pemasok")
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Products"
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
                        navigateToKategori()
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
            homeUiState = viewModel.pemasokUiState,
            retryAction = { viewModel.getPemasok() },
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePemasok(it.idPemasok)
                viewModel.getPemasok()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onMerkClick: () -> Unit = {},
    onDeleteClick: (Pemasok) -> Unit = {},
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.pemasok.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak ada data Pemasok",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                PemasokLayout(
                    pemasok = homeUiState.pemasok,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPemasok)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PemasokCard(
    pemasok: Pemasok,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemasok) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = Color(0xFF6C5CE7)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = pemasok.namaPemasok,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = pemasok.alamatPemasok,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = pemasok.teleponPemasok,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = { onDeleteClick(pemasok) }
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
fun PemasokLayout(
    pemasok: List<Pemasok>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pemasok) -> Unit,
    onDeleteClick: (Pemasok) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(pemasok) { item ->
            PemasokCard(
                pemasok = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = {
                    onDeleteClick(item)
                }
            )
        }
    }
}

// Keep existing Loading and Error states
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = stringResource(R.string.error),
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
        Button(
            onClick = retryAction,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}
