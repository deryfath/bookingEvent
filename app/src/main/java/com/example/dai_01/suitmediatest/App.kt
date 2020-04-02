package com.example.dai_01.suitmediatest

import android.app.Application
import com.example.dai_01.suitmediatest.dagger.component.AppComponent
import com.example.dai_01.suitmediatest.dagger.component.DaggerAppComponent
import com.example.dai_01.suitmediatest.dagger.module.ApiModule
import com.example.dai_01.suitmediatest.dagger.module.AppModule
import com.example.dai_01.suitmediatest.dagger.module.NetworkModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .networkModule(NetworkModule("https://raw.githubusercontent.com/"))
                    .apiModule(ApiModule())
                    .build()
    }
}