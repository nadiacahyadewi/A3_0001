package com.example.pam_tugasakhir.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_tugasakhir.ui.viewmodel.KategoriVM.KategoriHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.MerkHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.PemasokHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.PenyediaViewModel

object PilihMenu {

    @Composable
    fun merkOption(
        viewModel: MerkHomeVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val merkState = viewModel.merkUiState
        return when (merkState) {
            is com.example.pam_tugasakhir.ui.viewmodel.MerkVM.HomeUiState.Success -> merkState.merk.map { it.namaMerk }
            else -> emptyList()
        }
    }

    @Composable
    fun pemasokOption(
        viewModel: PemasokHomeVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val pemasokState = viewModel.pemasokUiState
        return when (pemasokState) {
            is com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.HomeUiState.Success -> pemasokState.pemasok.map { it.namaPemasok }
            else -> emptyList()
        }
    }

    @Composable
    fun kategoriOption(
        viewModel: KategoriHomeVM = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val kategoriState = viewModel.kategoriUiState
        return when (kategoriState) {
            is com.example.pam_tugasakhir.ui.viewmodel.KategoriVM.HomeUiState.Success -> kategoriState.kategori.map { it.namaKategori }
            else -> emptyList()
        }
    }
}