package com.example.pam_tugasakhir.ui.viewmodel.KategoriVM


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Kategori
import com.example.pam_tugasakhir.repository.KategoriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailKategoriUiState {
    object Loading : DetailKategoriUiState()
    data class Success(val kategori: Kategori) : DetailKategoriUiState()
    object Error : DetailKategoriUiState()
}

class KategoriDetailVM(private val repository: KategoriRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailKategoriUiState>(DetailKategoriUiState.Loading)
    val uiState: StateFlow<DetailKategoriUiState> = _uiState.asStateFlow()

    // Mendapatkan detail kategori berdasarkan id
    fun getKategoriById(idKategori: String) {
        viewModelScope.launch {
            _uiState.value = DetailKategoriUiState.Loading
            try {
                val kategori = repository.getKategoriById(idKategori)
                _uiState.value = DetailKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _uiState.value = DetailKategoriUiState.Error
            }
        }
    }

    // Memperbarui kategori
    fun updateKategori(idKategori: String, kategori: Kategori) {
        viewModelScope.launch {
            _uiState.value = DetailKategoriUiState.Loading
            try {
                repository.updateKategori(idKategori, kategori)
                _uiState.value = DetailKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _uiState.value = DetailKategoriUiState.Error
            }
        }
    }
}
