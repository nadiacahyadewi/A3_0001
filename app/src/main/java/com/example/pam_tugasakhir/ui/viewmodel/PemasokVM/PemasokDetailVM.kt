package com.example.pam_tugasakhir.ui.viewmodel.PemasokVM


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Pemasok
import com.example.pam_tugasakhir.repository.PemasokRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailPemasokUiState {
    object Loading : DetailPemasokUiState()
    data class Success(val pemasok: Pemasok) : DetailPemasokUiState()
    object Error : DetailPemasokUiState()
}

class PemasokDetailVM(private val repository: PemasokRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailPemasokUiState>(DetailPemasokUiState.Loading)
    val uiState: StateFlow<DetailPemasokUiState> = _uiState.asStateFlow()

    // Mendapatkan pemasok berdasarkan id
    fun getPemasokById(idPemasok: Int) {
        viewModelScope.launch {
            _uiState.value = DetailPemasokUiState.Loading
            try {
                val pemasok = repository.getPemasokById(idPemasok)
                _uiState.value = DetailPemasokUiState.Success(pemasok)
            } catch (e: Exception) {
                _uiState.value = DetailPemasokUiState.Error
            }
        }
    }

    // Memperbarui pemasok
    fun updatePemasok(idPemasok: Int, pemasok: Pemasok) {
        viewModelScope.launch {
            _uiState.value = DetailPemasokUiState.Loading
            try {
                repository.updatePemasok(idPemasok, pemasok)
                _uiState.value = DetailPemasokUiState.Success(pemasok)
            } catch (e: Exception) {
                _uiState.value = DetailPemasokUiState.Error
            }
        }
    }
}
