package com.example.dai_01.suitmediatest.activity.event

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.example.dai_01.suitmediatest.App
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.activity.guest.GuestPresenter
import com.example.dai_01.suitmediatest.activity.guest.RecyclerGuestAdapter
import com.example.dai_01.suitmediatest.model.DataEvent
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_guest.*
import javax.inject.Inject

class EventActivity : AppCompatActivity(),EventView {

    @Inject
    lateinit var presenter: EventPresenter
    private lateinit var adapter: RecyclerEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        App.component.inject(this)

        onAttach()
    }

    override fun onAttach() {
        presenter.onAttach(this)
        presenter.loadEventList()
    }

    override fun onLoadSuccessEvent(message: List<DataEvent>) {
        rv_event.layoutManager = LinearLayoutManager(this)
        adapter= RecyclerEventAdapter(this,message)
        rv_event.adapter=adapter
    }

    override fun onLoadFailedEvent(message: String) {
        println("MESSAGE ERROR $message")
    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
