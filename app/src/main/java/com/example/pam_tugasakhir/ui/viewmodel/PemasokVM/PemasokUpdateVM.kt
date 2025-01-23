package com.example.pam_tugasakhir.ui.viewmodel.PemasokVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Pemasok
import com.example.pam_tugasakhir.repository.PemasokRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UpdatePemasokEvent(
    val idPemasok: String,
    val namaPemasok: String,
    val alamatPemasok: String,
    val teleponPemasok: String
)

sealed class UpdatePemasokUiState {
    object Idle : UpdatePemasokUiState()
    object Loading : UpdatePemasokUiState()
    data class Success(val pemasok: Pemasok) : UpdatePemasokUiState()
    data class Error(val message: String) : UpdatePemasokUiState()
}

class PemasokUpdateVM(
    private val pemasokRepository: PemasokRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UpdatePemasokUiState>(UpdatePemasokUiState.Idle)
    val uiState: StateFlow<UpdatePemasokUiState> = _uiState

    private var currentFormData: UpdatePemasokEvent? = null

    fun loadPemasokData(idPemasok: String) {
        _uiState.value = UpdatePemasokUiState.Loading
        viewModelScope.launch {
            try {
                val pemasok = pemasokRepository.getPemasokById(idPemasok)
                currentFormData = UpdatePemasokEvent(
                    idPemasok = pemasok.idPemasok,
                    namaPemasok = pemasok.namaPemasok,
                    alamatPemasok = pemasok.alamatPemasok,
                    teleponPemasok = pemasok.teleponPemasok
                )
                _uiState.value = UpdatePemasokUiState.Success(pemasok)
            } catch (e: Exception) {
                _uiState.value = UpdatePemasokUiState.Error("Failed to load supplier data: ${e.message}")
            }
        }
    }

    fun updateUiState(event: UpdatePemasokEvent) {
        currentFormData = event
    }

    fun updatePemasok() {
        val formData = currentFormData
        if (formData == null) {
            _uiState.value = UpdatePemasokUiState.Error("Form data is not initialized")
            return
        }

        _uiState.value = UpdatePemasokUiState.Loading
        viewModelScope.launch {
            try {
                val pemasok = Pemasok(
                    idPemasok = formData.idPemasok,
                    namaPemasok = formData.namaPemasok,
                    alamatPemasok = formData.alamatPemasok,
                    teleponPemasok = formData.teleponPemasok
                )
                pemasokRepository.updatePemasok(formData.idPemasok, pemasok)
                _uiState.value = UpdatePemasokUiState.Success(pemasok)
            } catch (e: Exception) {
                _uiState.value = UpdatePemasokUiState.Error("Failed to update supplier: ${e.message}")
            }
        }
    }
}
