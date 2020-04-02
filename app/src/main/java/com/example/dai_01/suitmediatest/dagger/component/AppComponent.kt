package com.example.dai_01.suitmediatest.dagger.component

import com.example.dai_01.suitmediatest.activity.event.EventActivity
import com.example.dai_01.suitmediatest.activity.guest.GuestActivity
import com.example.dai_01.suitmediatest.activity.login.LoginActivity
import com.example.dai_01.suitmediatest.activity.main.MainActivity
import com.example.dai_01.suitmediatest.dagger.module.ApiModule
import com.example.dai_01.suitmediatest.dagger.module.AppModule
import com.example.dai_01.suitmediatest.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        ApiModule::class
))

interface AppComponent {

    fun inject(guestActivity: GuestActivity)
    fun inject(eventActivity: EventActivity)
}