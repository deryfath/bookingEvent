package com.example.dai_01.suitmediatest.activity.guest

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

class GuestPresenter @Inject constructor(
@Authorized
val api: ApiService,
val retrofit: Retrofit
): Presenter<GuestView> {

    private var view : GuestView? = null
    var guestListDisposables = Disposables.empty()
    var guestListMoreDisposables = Disposables.empty()

    override fun onAttach(view: GuestView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun loadGuestList(page:Int){

        guestListDisposables.dispose()
        guestListDisposables = api.getGuest(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadSuccessGuest(res.data)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadFailedGuest("Error: ${body}")
                    } else {
                        view?.onLoadFailedGuest(err.localizedMessage)
                    }
                })
    }


    fun loadMoreGuestList(page:Int){

        guestListMoreDisposables.dispose()
        guestListMoreDisposables = api.getGuest(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadMoreSuccessGuest(res.data)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadMoreFailedGuest("Error: ${body}")
                    } else {
                        view?.onLoadMoreFailedGuest(err.localizedMessage)
                    }
                })
    }
}