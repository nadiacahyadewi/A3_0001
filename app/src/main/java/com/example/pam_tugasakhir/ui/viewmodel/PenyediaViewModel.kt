package com.example.pam_tugasakhir.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pam_tugasakhir.ProdukApplications
import com.example.pam_tugasakhir.ui.viewmodel.KategoriVM.KategoriHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.MerkDetailVM
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.MerkHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.MerkVM.MerkInsertVM
import com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.PemasokDetailVM
import com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.PemasokHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.PemasokVM.PemasokInsertVM
import com.example.pam_tugasakhir.ui.viewmodel.ProdukVM.ProdukHomeVM
import com.example.pam_tugasakhir.ui.viewmodel.produk.ProdukDetailVM
import com.example.pam_tugasakhir.ui.viewmodel.produk.ProdukInsertVM


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { ProdukHomeVM(aplikasiproduk().container.produkRepository) }
        initializer { ProdukInsertVM(aplikasiproduk().container.produkRepository) }
        initializer { ProdukDetailVM(aplikasiproduk().container.produkRepository) }
        initializer { PemasokHomeVM(aplikasiproduk().container.pemasokRepository) }
        initializer { PemasokInsertVM(aplikasiproduk().container.pemasokRepository) }
        initializer { PemasokDetailVM(aplikasiproduk().container.pemasokRepository) }
        initializer { MerkHomeVM(aplikasiproduk().container.merkRepository) }
        initializer { MerkInsertVM(aplikasiproduk().container.merkRepository) }
        initializer { MerkDetailVM(aplikasiproduk().container.merkRepository) }
        initializer { KategoriHomeVM(aplikasiproduk().container.kategoriRepository) }
    }
}

fun CreationExtras.aplikasiproduk():ProdukApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProdukApplications)