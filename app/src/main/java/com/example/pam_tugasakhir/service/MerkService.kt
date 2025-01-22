package com.example.pam_tugasakhir.service


import com.example.pam_tugasakhir.model.Merk
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MerkService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertmerk.php")
    suspend fun insertMerk(@Body merk: Merk)

    @GET("readmerk.php")
    suspend fun getAllMerk(): List<Merk>

    @GET("detailmerk.php")
    suspend fun getMerkById(@Query("id_merk") idMerk: String): Merk

    @PUT("editmerk.php")
    suspend fun updateMerk(@Query("id_merk") idMerk: String, @Body merk: Merk)

    @DELETE("deletemerk.php")
    suspend fun deleteMerk(@Query("id_merk") idMerk: String): Response<Void>
}
