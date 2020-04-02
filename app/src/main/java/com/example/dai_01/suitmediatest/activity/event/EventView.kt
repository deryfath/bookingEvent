package com.example.dai_01.suitmediatest.activity.event

import com.example.dai_01.suitmediatest.model.DataEvent
import com.example.dai_01.suitmediatest.model.EventResponse
import com.example.dai_01.suitmediatest.model.GuestResponse
import com.example.dai_01.suitmediatest.model.ResponseData
import com.example.dai_01.suitmediatest.mvp.View

interface EventView: View {
    fun onLoadSuccessEvent(message : List<DataEvent>)

    fun onLoadFailedEvent(message: String)
}