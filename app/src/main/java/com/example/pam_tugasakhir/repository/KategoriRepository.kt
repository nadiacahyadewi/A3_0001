package com.example.pam_tugasakhir.repository

import com.example.pam_tugasakhir.model.Kategori
import com.example.pam_tugasakhir.service.KategoriService
import okio.IOException

interface KategoriRepository {
    suspend fun insertKategori(kategori: Kategori)

    suspend fun getKategori(): List<Kategori>

    suspend fun updateKategori(idKategori: Int, kategori: Kategori)

    suspend fun deleteKategori(idKategori: Int)

    suspend fun getKategoriById(idKategori: Int): Kategori
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {
    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(idKategori: Int, kategori: Kategori) {
        kategoriApiService.updateKategori(idKategori, kategori)
    }

    override suspend fun deleteKategori(idKategori: Int) {
        try {
            val response = kategoriApiService.deleteKategori(idKategori)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kategori. HTTP Status Code: " +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKategori(): List<Kategori> =
        kategoriApiService.getAllKategori()

    override suspend fun getKategoriById(idKategori: Int): Kategori {
        return kategoriApiService.getKategoriById(idKategori)
    }
}
