package com.example.pam_tugasakhir.repository

import com.example.pam_tugasakhir.model.Produk
import com.example.pam_tugasakhir.service.ProdukService
import okio.IOException

interface ProdukRepository {
    suspend fun insertProduk(produk: Produk)

    suspend fun getProduk(): List<Produk>

    suspend fun updateProduk(idProduk: Int, produk: Produk)

    suspend fun deleteProduk(idProduk: Int)

    suspend fun getProdukById(idProduk: Int): Produk
}

class NetworkProdukRepository(
    private val produkApiService: ProdukService
) : ProdukRepository {
    override suspend fun insertProduk(produk: Produk) {
        produkApiService.insertProduk(produk)
    }

    override suspend fun updateProduk(idProduk: Int, produk: Produk) {
        produkApiService.updateProduk(idProduk, produk)
    }

    override suspend fun deleteProduk(idProduk: Int) {
        try {
            val response = produkApiService.deleteProduk(idProduk)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete produk. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProduk(): List<Produk> =
        produkApiService.getAllProduk()

    override suspend fun getProdukById(idProduk: Int): Produk {
        return produkApiService.getProdukById(idProduk)
    }
}