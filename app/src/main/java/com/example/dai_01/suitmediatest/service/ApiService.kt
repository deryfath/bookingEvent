package com.example.dai_01.suitmediatest.service

import com.example.dai_01.suitmediatest.model.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("/api/users?per_page=10")
    fun getGuest(@Query("page")page:Int): Observable<GuestResponse>

    @GET("/api/events?per_page=10")
    fun getEvent(): Observable<EventResponse>

}