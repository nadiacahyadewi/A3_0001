package com.example.pam_tugasakhir.ui.viewmodel.MerkVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Merk
import com.example.pam_tugasakhir.repository.MerkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdateMerkEvent(
    val idMerk: Int = 0,
    val namaMerk: String,
    val deskripsiMerk: String
)

sealed class UpdateMerkUiState {
    object Idle : UpdateMerkUiState()
    object Loading : UpdateMerkUiState()
    data class Success(val merk: Merk) : UpdateMerkUiState()
    data class Error(val message: String) : UpdateMerkUiState()
}

class MerkUpdateVM(
    private val merkRepository: MerkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdateMerkUiState>(UpdateMerkUiState.Idle)
    val uiState: StateFlow<UpdateMerkUiState> = _uiState

    private var currentFormData: UpdateMerkEvent? = null

    fun loadMerkData(idMerk: Int) {
        _uiState.value = UpdateMerkUiState.Loading
        viewModelScope.launch {
            try {
                val merk = merkRepository.getMerkById(idMerk)
                currentFormData = UpdateMerkEvent(
                    idMerk = merk.idMerk,
                    namaMerk = merk.namaMerk,
                    deskripsiMerk = merk.deskripsiMerk
                )
                _uiState.value = UpdateMerkUiState.Success(merk)
            } catch (e: Exception) {
                _uiState.value = UpdateMerkUiState.Error("Failed to load brand data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdateMerkEvent) {
        currentFormData = event
    }

    fun updateMerk() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdateMerkUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdateMerkUiState.Loading
        viewModelScope.launch {
            try {
                val merk = Merk(
                    idMerk = formData.idMerk,
                    namaMerk = formData.namaMerk,
                    deskripsiMerk = formData.deskripsiMerk
                )
                merkRepository.updateMerk(formData.idMerk, merk)
                _uiState.value = UpdateMerkUiState.Success(merk)
            } catch (e: Exception) {
                _uiState.value = UpdateMerkUiState.Error("Failed to update brand: ${e.message}")
            }
        }
    }
}
