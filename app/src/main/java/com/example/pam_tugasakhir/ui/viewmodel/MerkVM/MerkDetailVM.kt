package com.example.pam_tugasakhir.ui.viewmodel.MerkVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Merk
import com.example.pam_tugasakhir.repository.MerkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailMerkUiState {
    object Loading : DetailMerkUiState()
    data class Success(val merk: Merk) : DetailMerkUiState()
    object Error : DetailMerkUiState()
}

class MerkDetailVM(private val repository: MerkRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailMerkUiState>(DetailMerkUiState.Loading)
    val uiState: StateFlow<DetailMerkUiState> = _uiState.asStateFlow()

    // Mendapatkan detail merk berdasarkan id
    fun getMerkById(idMerk: String) {
        viewModelScope.launch {
            _uiState.value = DetailMerkUiState.Loading
            try {
                val merk = repository.getMerkById(idMerk)
                _uiState.value = DetailMerkUiState.Success(merk)
            } catch (e: Exception) {
                _uiState.value = DetailMerkUiState.Error
            }
        }
    }

    // Memperbarui merk
    fun updateMerk(idMerk: String, merk: Merk) {
        viewModelScope.launch {
            _uiState.value = DetailMerkUiState.Loading
            try {
                repository.updateMerk(idMerk, merk)
                _uiState.value = DetailMerkUiState.Success(merk)
            } catch (e: Exception) {
                _uiState.value = DetailMerkUiState.Error
            }
        }
    }
}
