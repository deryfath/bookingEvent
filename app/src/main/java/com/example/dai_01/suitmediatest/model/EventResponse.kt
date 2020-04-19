package com.example.dai_01.suitmediatest.model

data class EventResponse(
    val ad: AdEvent,
    val `data`: List<DataEvent>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)

data class AdEvent(
    val company: String,
    val text: String,
    val url: String
)

data class DataEvent(
    val color: String,
    val id: Int,
    val name: String,
    val pantone_value: String,
    val year: Int,
    var image:String,
    var location:String
)