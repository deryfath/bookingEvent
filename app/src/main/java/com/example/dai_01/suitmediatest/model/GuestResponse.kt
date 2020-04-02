package com.example.dai_01.suitmediatest.model

data class GuestResponse(
    val listGuest: List<DataGuest>
)

data class DataGuest(
    val birthdate: String,
    val id: Int,
    val name: String
)