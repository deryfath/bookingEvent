package com.example.dai_01.suitmediatest.activity.guest

import com.example.dai_01.suitmediatest.model.DataGuest
import com.example.dai_01.suitmediatest.model.GuestResponse
import com.example.dai_01.suitmediatest.model.ResponseData
import com.example.dai_01.suitmediatest.mvp.View

interface GuestView: View {
    fun onLoadSuccessGuest(message: List<DataGuest>)

    fun onLoadFailedGuest(message: String)
}