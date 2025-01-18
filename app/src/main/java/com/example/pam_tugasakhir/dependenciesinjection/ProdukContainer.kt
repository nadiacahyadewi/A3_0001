package com.example.pam_tugasakhir.dependenciesinjection

import com.example.pam_tugasakhir.repository.NetworkProdukRepository
import com.example.pam_tugasakhir.repository.ProdukRepository
import com.example.pam_tugasakhir.service.ProdukService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val produkRepository: ProdukRepository
}

class ProdukContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:80/pam_tugasakhir/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val produkService: ProdukService by lazy {
        retrofit.create(ProdukService::class.java)
    }

    override val produkRepository: ProdukRepository by lazy {
        NetworkProdukRepository(produkService)
    }
}
