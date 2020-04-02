package com.example.dai_01.suitmediatest.service

import com.example.dai_01.suitmediatest.model.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("/deryfath/dummyData/master/dummyGuest")
    fun getGuest(): Observable<ResponseData<List<DataGuest>>>

    @GET("/deryfath/dummyData/master/dummyEvent")
    fun getEvent(): Observable<ResponseData<List<DataEvent>>>

}