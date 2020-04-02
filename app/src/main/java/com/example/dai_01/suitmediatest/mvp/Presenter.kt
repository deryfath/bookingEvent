package com.example.dai_01.suitmediatest.mvp

interface Presenter<in T: View> {

    fun onAttach(view: T)
    fun onDetach()

}