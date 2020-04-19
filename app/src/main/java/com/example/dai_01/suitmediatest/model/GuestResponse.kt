package com.example.dai_01.suitmediatest.model

data class GuestResponse(
    val ad: Ad,
    val `data`: List<DataGuest>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)

data class Ad(
    val company: String,
    val text: String,
    val url: String
)

data class DataGuest(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
)