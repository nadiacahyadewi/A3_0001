package com.example.pam_tugasakhir.service

import com.example.pam_tugasakhir.model.Pemasok
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PemasokService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertpemasok.php")
    suspend fun insertPemasok(@Body pemasok: Pemasok)

    @GET("readpemasok.php")
    suspend fun getAllPemasok(): List<Pemasok>

    @GET("detailpemasok.php")
    suspend fun getPemasokById(@Query("id_pemasok") idPemasok: Int): Pemasok

    @PUT("editpemasok.php")
    suspend fun updatePemasok(@Query("id_pemasok") idPemasok: Int, @Body pemasok: Pemasok)

    @DELETE("deletepemasok.php")
    suspend fun deletePemasok(@Query("id_pemasok") idPemasok: Int): Response<Void>
}
