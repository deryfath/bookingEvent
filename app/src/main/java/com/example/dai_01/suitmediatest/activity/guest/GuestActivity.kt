package com.example.dai_01.suitmediatest.activity.guest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
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



class GuestActivity : AppCompatActivity(),GuestView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var presenter: GuestPresenter
    private lateinit var adapter: RecyclerGuestAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        App.component.inject(this)
        refreshLayout.setOnRefreshListener(this)
        onAttach()
    }

    override fun onAttach() {
        presenter.onAttach(this)
        presenter.loadGuestList()
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        presenter.loadGuestList()
    }

    fun isPrime(number:Int):Boolean {

            var i: Int
            var m = 0
            var flag = 0
            m = number / 2
            if (number == 0 || number == 1) {
                return false
            } else {
                i = 2
                while (i <= m) {
                    if (number % i == 0) {
                        return false
                        flag = 1
                        break
                    }
                    i++
                }
                if (flag == 0) {
                    return true
                }
            }
        return false
    }

    override fun onLoadSuccessGuest(message: List<DataGuest>) {
        refreshLayout.isRefreshing = false
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
