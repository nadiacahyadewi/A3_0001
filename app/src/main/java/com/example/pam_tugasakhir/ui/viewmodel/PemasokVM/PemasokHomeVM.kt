package com.example.pam_tugasakhir.ui.viewmodel.PemasokVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.pam_tugasakhir.model.Pemasok
import com.example.pam_tugasakhir.repository.PemasokRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val pemasok: List<Pemasok>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class PemasokHomeVM(private val pemasokRepository: PemasokRepository) : ViewModel() {
    var pemasokUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPemasok()
    }

    fun getPemasok() {
        viewModelScope.launch {
            pemasokUiState = HomeUiState.Loading
            pemasokUiState = try {
                HomeUiState.Success(pemasokRepository.getPemasok())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deletePemasok(id: String) {
        viewModelScope.launch {
            try {
                pemasokRepository.deletePemasok(id)
            } catch (e: IOException) {
                pemasokUiState = HomeUiState.Error
            } catch (e: HttpException) {
                pemasokUiState = HomeUiState.Error
            }
        }
    }
}
