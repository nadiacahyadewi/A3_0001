package com.example.pam_tugasakhir.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Merk(
    @SerialName("id_merk")
    val idMerk: Int = 0,

    @SerialName("nama_merk")
    val namaMerk: String,

    @SerialName("deskripsi_merk")
    val deskripsiMerk: String
)

