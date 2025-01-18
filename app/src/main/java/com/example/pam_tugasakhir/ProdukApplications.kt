package com.example.pam_tugasakhir

import android.app.Application
import com.example.pam_tugasakhir.dependenciesinjection.AppContainer
import com.example.pam_tugasakhir.dependenciesinjection.ProdukContainer

class ProdukApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ProdukContainer()
    }
}