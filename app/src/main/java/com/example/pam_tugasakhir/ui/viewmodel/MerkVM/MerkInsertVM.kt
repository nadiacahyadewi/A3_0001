package com.example.pam_tugasakhir.ui.viewmodel.MerkVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Merk
import com.example.pam_tugasakhir.repository.MerkRepository
import kotlinx.coroutines.launch

class MerkInsertVM(private val merkRepository: MerkRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMerkState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    fun insertMerk() {
        viewModelScope.launch {
            try {
                val merk = uiState.insertUiEvent.toMerk()
                merkRepository.insertMerk(merk)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the error properly, e.g., updating a UI state or logging
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idMerk: Int = 0,
    val namaMerk: String = "",
    val deskripsiMerk: String = ""
)

fun InsertUiEvent.toMerk(): Merk = Merk(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)

fun Merk.toUiStateMerk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Merk.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idMerk = idMerk,
    namaMerk = namaMerk,
    deskripsiMerk = deskripsiMerk
)
