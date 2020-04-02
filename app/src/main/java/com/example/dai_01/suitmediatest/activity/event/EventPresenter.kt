package com.example.dai_01.suitmediatest.activity.event

import com.example.dai_01.suitmediatest.dagger.qualifier.Authorized
import com.example.dai_01.suitmediatest.extension.errorConverter
import com.example.dai_01.suitmediatest.mvp.Presenter
import com.example.dai_01.suitmediatest.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class EventPresenter @Inject constructor(
        @Authorized val api: ApiService,
        val retrofit: Retrofit
): Presenter<EventView> {

    private var view : EventView? = null
    var eventListDisposables = Disposables.empty()

    override fun onAttach(view: EventView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun loadEventList(){

        eventListDisposables.dispose()
        eventListDisposables = api.getEvent()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadSuccessEvent(res.data)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadFailedEvent("Error: ${body}")
                    } else {
                        view?.onLoadFailedEvent(err.localizedMessage)
                    }
                })
    }


}