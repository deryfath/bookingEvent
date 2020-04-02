package com.example.dai_01.suitmediatest.activity.guest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.dai_01.suitmediatest.App
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.model.DataGuest
import com.example.dai_01.suitmediatest.model.GuestResponse
import com.example.dai_01.suitmediatest.model.ResponseData
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_guest.*
import javax.inject.Inject

class GuestActivity : AppCompatActivity(),GuestView {

    @Inject
    lateinit var presenter: GuestPresenter
    private lateinit var adapter: RecyclerGuestAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        App.component.inject(this)

        onAttach()
    }

    override fun onAttach() {
        presenter.onAttach(this)
        presenter.loadGuestList()
    }

    override fun onLoadSuccessGuest(message: List<DataGuest>) {
        rv_guest.layoutManager = GridLayoutManager(this,2)
        adapter= RecyclerGuestAdapter(this,message)
        rv_guest.adapter=adapter
    }

    override fun onLoadFailedGuest(message: String) {
        println("MESSAGE ERROR $message")

    }

    override fun onDetach() {

    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
