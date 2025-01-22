package com.example.pam_tugasakhir.service


import com.example.pam_tugasakhir.model.Kategori
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KategoriService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertkategori.php")
    suspend fun insertKategori(@Body kategori: Kategori)

    @GET("readkategori.php")
    suspend fun getAllKategori(): List<Kategori>

    @GET("detailkategori.php")
    suspend fun getKategoriById(@Query("id_kategori") idKategori: String): Kategori

    @PUT("editkategori.php")
    suspend fun updateKategori(@Query("id_kategori") idKategori: String, @Body kategori: Kategori)

    @DELETE("deletekategori.php")
    suspend fun deleteKategori(@Query("id_kategori") idKategori: String): Response<Void>
}
