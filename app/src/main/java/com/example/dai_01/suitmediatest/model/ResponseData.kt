package com.example.dai_01.suitmediatest.model


data class ResponseData<T>(
        var status: Int = 0,
        var msg: String = "",
        var data: T
)