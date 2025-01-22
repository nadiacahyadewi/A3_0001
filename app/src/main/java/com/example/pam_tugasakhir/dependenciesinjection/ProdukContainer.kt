package com.example.pam_tugasakhir.dependenciesinjection

import com.example.pam_tugasakhir.repository.KategoriRepository
import com.example.pam_tugasakhir.repository.MerkRepository
import com.example.pam_tugasakhir.repository.NetworkKategoriRepository
import com.example.pam_tugasakhir.repository.NetworkMerkRepository
import com.example.pam_tugasakhir.repository.NetworkPemasokRepository
import com.example.pam_tugasakhir.repository.NetworkProdukRepository
import com.example.pam_tugasakhir.repository.PemasokRepository
import com.example.pam_tugasakhir.repository.ProdukRepository
import com.example.pam_tugasakhir.service.KategoriService
import com.example.pam_tugasakhir.service.MerkService
import com.example.pam_tugasakhir.service.PemasokService
import com.example.pam_tugasakhir.service.ProdukService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val produkRepository: ProdukRepository
    val pemasokRepository: PemasokRepository
    val merkRepository: MerkRepository
    val kategoriRepository: KategoriRepository
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

    private val pemasokService: PemasokService by lazy {
        retrofit.create(PemasokService::class.java)
    }
    override val pemasokRepository: PemasokRepository by lazy {
        NetworkPemasokRepository(pemasokService)
    }

    private val merkService: MerkService by lazy {
        retrofit.create(MerkService::class.java)
    }
    override val merkRepository: MerkRepository by lazy {
        NetworkMerkRepository(merkService)
    }

    private val kategoriService: KategoriService by lazy {
        retrofit.create(KategoriService::class.java)
    }
    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }
}
