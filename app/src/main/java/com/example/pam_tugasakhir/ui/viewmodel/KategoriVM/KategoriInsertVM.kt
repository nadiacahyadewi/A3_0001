package com.example.pam_tugasakhir.ui.viewmodel.KategoriVM



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Kategori
import com.example.pam_tugasakhir.repository.KategoriRepository
import kotlinx.coroutines.launch

class KategoriInsertVM(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertKategoriState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    fun insertKategori() {
        viewModelScope.launch {
            try {
                val kategori = uiState.insertUiEvent.toKategori()
                kategoriRepository.insertKategori(kategori)
            } catch (e: Exception) {
                e.printStackTrace()
                // Tangani error, misalnya dengan memperbarui UI state atau logging
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idKategori: String = "",
    val namaKategori: String = "",
    val deskripsiKategori: String = ""
)

fun InsertUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

fun Kategori.toUiStateKategori(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Kategori.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)
