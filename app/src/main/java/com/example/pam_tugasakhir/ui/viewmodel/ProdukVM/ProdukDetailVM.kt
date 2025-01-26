package com.example.pam_tugasakhir.ui.viewmodel.produk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_tugasakhir.model.Produk
import com.example.pam_tugasakhir.repository.ProdukRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailProdukUiState {
    object Loading : DetailProdukUiState()
    data class Success(val produk: Produk) : DetailProdukUiState()
    object Error : DetailProdukUiState()
}

class ProdukDetailVM(private val repository: ProdukRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailProdukUiState>(DetailProdukUiState.Loading)
    val uiState: StateFlow<DetailProdukUiState> = _uiState.asStateFlow()

    // Mendapatkan produk berdasarkan id
    fun getProdukById(idProduk: Int) {
        viewModelScope.launch {
            _uiState.value = DetailProdukUiState.Loading
            try {
                val produk = repository.getProdukById(idProduk)
                _uiState.value = DetailProdukUiState.Success(produk)
            } catch (e: Exception) {
                _uiState.value = DetailProdukUiState.Error
            }
        }
    }

    // Memperbarui produk
    fun updateProduk(idProduk: Int, produk: Produk) {
        viewModelScope.launch {
            _uiState.value = DetailProdukUiState.Loading
            try {
                repository.updateProduk(idProduk, produk)
                _uiState.value = DetailProdukUiState.Success(produk)
            } catch (e: Exception) {
                _uiState.value = DetailProdukUiState.Error
            }
        }
    }
}
