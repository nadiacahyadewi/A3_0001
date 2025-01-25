package com.example.pam_tugasakhir.repository

import com.example.pam_tugasakhir.model.Merk
import com.example.pam_tugasakhir.service.MerkService
import okio.IOException

interface MerkRepository {
    suspend fun insertMerk(merk: Merk)

    suspend fun getMerk(): List<Merk>

    suspend fun updateMerk(idMerk: Int, merk: Merk)

    suspend fun deleteMerk(idMerk: Int)

    suspend fun getMerkById(idMerk: Int): Merk
}

class NetworkMerkRepository(
    private val merkApiService: MerkService
) : MerkRepository {
    override suspend fun insertMerk(merk: Merk) {
        merkApiService.insertMerk(merk)
    }

    override suspend fun updateMerk(idMerk: Int, merk: Merk) {
        merkApiService.updateMerk(idMerk, merk)
    }

    override suspend fun deleteMerk(idMerk: Int) {
        try {
            val response = merkApiService.deleteMerk(idMerk)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete merk. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMerk(): List<Merk> =
        merkApiService.getAllMerk()

    override suspend fun getMerkById(idMerk: Int): Merk {
        return merkApiService.getMerkById(idMerk)
    }
}
