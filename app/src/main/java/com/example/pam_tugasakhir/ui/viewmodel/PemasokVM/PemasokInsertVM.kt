package com.example.pam_tugasakhir.ui.viewmodel.PemasokVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Pemasok
import com.example.pam_tugasakhir.repository.PemasokRepository
import kotlinx.coroutines.launch

class PemasokInsertVM(private val pemasokRepository: PemasokRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPemasokState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPemasok() {
        viewModelScope.launch {
            try {
                pemasokRepository.insertPemasok(uiState.insertUiEvent.toPemasok())
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
    val idPemasok: String = "",
    val namaPemasok: String = "",
    val alamatPemasok: String = "",
    val teleponPemasok: String = ""
)

fun InsertUiEvent.toPemasok(): Pemasok = Pemasok(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)

fun Pemasok.toUiStatePemasok(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Pemasok.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idPemasok = idPemasok,
    namaPemasok = namaPemasok,
    alamatPemasok = alamatPemasok,
    teleponPemasok = teleponPemasok
)
