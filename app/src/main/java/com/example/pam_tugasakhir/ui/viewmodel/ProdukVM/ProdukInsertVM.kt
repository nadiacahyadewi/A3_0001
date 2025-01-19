package com.example.pam_tugasakhir.ui.viewmodel.produk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Produk
import com.example.pam_tugasakhir.repository.ProdukRepository
import kotlinx.coroutines.launch

class ProdukInsertVM(private val produkRepository: ProdukRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertProdukState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertProduk() {
        viewModelScope.launch {
            try {
                produkRepository.insertProduk(uiState.insertUiEvent.toProduk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idProduk: String = "",
    val namaProduk: String = "",
    val deskripsiProduk: String = "",
    val harga: String = "",
    val stok: String = "",
    val idKategori: String = "",
    val idPemasok: String = "",
    val idMerk: String = ""
)

fun InsertUiEvent.toProduk(): Produk = Produk(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)

fun Produk.toUiStateProduk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Produk.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProduk = idProduk,
    namaProduk = namaProduk,
    deskripsiProduk = deskripsiProduk,
    harga = harga,
    stok = stok,
    idKategori = idKategori,
    idPemasok = idPemasok,
    idMerk = idMerk
)
