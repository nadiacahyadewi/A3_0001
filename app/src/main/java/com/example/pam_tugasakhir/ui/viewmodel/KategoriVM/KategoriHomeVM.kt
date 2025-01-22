package com.example.pam_tugasakhir.ui.viewmodel.KategoriVM


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.pam_tugasakhir.model.Kategori
import com.example.pam_tugasakhir.repository.KategoriRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val kategori: List<Kategori>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class KategoriHomeVM(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var kategoriUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            kategoriUiState = HomeUiState.Loading
            kategoriUiState = try {
                HomeUiState.Success(kategoriRepository.getKategori())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteKategori(id: String) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(id)
            } catch (e: IOException) {
                kategoriUiState = HomeUiState.Error
            } catch (e: HttpException) {
                kategoriUiState = HomeUiState.Error
            }
        }
    }
}
