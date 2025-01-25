package com.example.pam_tugasakhir.ui.viewmodel.MerkVM



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.pam_tugasakhir.model.Merk
import com.example.pam_tugasakhir.repository.MerkRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeUiState {
    data class Success(val merk: List<Merk>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class MerkHomeVM(private val merkRepository: MerkRepository) : ViewModel() {
    var merkUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMerk()
    }

    fun getMerk() {
        viewModelScope.launch {
            merkUiState = HomeUiState.Loading
            merkUiState = try {
                HomeUiState.Success(merkRepository.getMerk())
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteMerk(id: Int) {
        viewModelScope.launch {
            try {
                merkRepository.deleteMerk(id)
            } catch (e: IOException) {
                merkUiState = HomeUiState.Error
            } catch (e: HttpException) {
                merkUiState = HomeUiState.Error
            }
        }
    }
}
