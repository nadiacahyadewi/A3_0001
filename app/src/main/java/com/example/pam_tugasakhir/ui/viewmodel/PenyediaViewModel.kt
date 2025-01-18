package com.example.pam_tugasakhir.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pam_tugasakhir.ProdukApplications
import com.example.pam_tugasakhir.ui.viewmodel.ProdukVM.ProdukHomeVM

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { ProdukHomeVM(aplikasiproduk().container.produkRepository) }
    }
}

fun CreationExtras.aplikasiproduk():ProdukApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProdukApplications)