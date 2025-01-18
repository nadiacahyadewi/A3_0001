package com.example.pam_tugasakhir.ui.viewmodel.ProdukVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.pam_tugasakhir.model.Produk
import com.example.pam_tugasakhir.repository.ProdukRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val produk: List<Produk>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class ProdukHomeVM(private val produkRepository: ProdukRepository) : ViewModel() {
    var produkUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getProduk()
    }

    fun getProduk() {
        viewModelScope.launch {
            produkUiState = HomeUiState.Loading
            produkUiState = try {
                HomeUiState.Success(produkRepository.getProduk())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteProduk(id: String) {
        viewModelScope.launch {
            try {
                produkRepository.deleteProduk(id)
            } catch (e: IOException) {
                produkUiState = HomeUiState.Error
            } catch (e: HttpException) {
                produkUiState = HomeUiState.Error
            }
        }
    }
}
