package com.example.pam_tugasakhir.service

import com.example.pam_tugasakhir.model.Produk
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query



interface ProdukService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("insertproduk.php")
    suspend fun insertProduk(@Body produk: Produk)

    @GET("readproduk.php")
    suspend fun getAllProduk(): List<Produk>

    @GET("detailproduk.php/{id_produk}")
    suspend fun getProdukById(@Query("id_produk") idProduk: Int): Produk

    @PUT("editproduk.php/{id_produk}")
    suspend fun updateProduk(@Query("id_produk") idProduk: Int, @Body produk: Produk)

    @DELETE("deleteproduk.php/{id_produk}")
    suspend fun deleteProduk(@Query("id_produk") idProduk: Int): Response<Void>
}
