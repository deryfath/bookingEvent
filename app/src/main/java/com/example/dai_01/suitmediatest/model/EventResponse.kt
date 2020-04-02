package com.example.dai_01.suitmediatest.model

data class EventResponse(
    val listEvent: List<DataEvent>
)

data class DataEvent(
    val date: String,
    val image: String,
    val name: String
)