package com.example.dai_01.suitmediatest.activity.guest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
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

    var page: Int = 1

    var MAX_SIZE : Int = 10

    var mModule = ArrayList<DataGuest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        App.component.inject(this)
        refreshLayout.setOnRefreshListener(this)
        onAttach()
    }

    override fun onAttach() {
        presenter.onAttach(this)
        presenter.loadGuestList(page)
        initiateRecyclerView()
    }

    private fun initiateRecyclerView() {

        adapter = RecyclerGuestAdapter(this,mModule){
        }
        adapter.setLoadMoreListener(object : RecyclerGuestAdapter.OnLoadMoreListener {
            override fun onLoadMore() {
                rv_guest.post {
                    if (mModule.size >= MAX_SIZE){
                        progress_module_more.visibility = View.VISIBLE
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            page += 1
                            loadMore(page)
                        }, 1500)
                    }else{
                        progress_module_more.visibility = View.GONE
                    }

                }
            }
        })
        rv_guest.layoutManager = WrapContentLinearLayoutManager()
        rv_guest.adapter = adapter

    }

    fun loadMore(page:Int){
        adapter.notifyItemInserted(mModule.size)
        presenter.loadMoreGuestList(page)
    }

    inner class WrapContentLinearLayoutManager : GridLayoutManager(this,2) {
        //... constructor
        override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
            try {
                super.onLayoutChildren(recycler, state)
            } catch (e: IndexOutOfBoundsException) {
                Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens")
            }

        }
    }

    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        presenter.loadGuestList(1)
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
        mModule.clear()
        if (message.size > 0){
            if (message.size >= MAX_SIZE){
                adapter!!.setMoreDataAvailable(true)
            }else{
                adapter!!.setMoreDataAvailable(false)
            }
            lyLoadMore.visibility = View.GONE
            mModule.addAll(message)
            adapter!!.notifyDataChanged()
        }else{
            adapter!!.notifyDataChanged()
            pbMore.visibility = View.INVISIBLE
            txtMore.text = "Tidak ada data"
        }
    }

    override fun onLoadFailedGuest(message: String) {
        refreshLayout.isRefreshing = false
        pbMore.visibility = View.INVISIBLE
        txtMore.text = message

    }

    override fun onLoadMoreSuccessGuest(message: List<DataGuest>) {
        progress_module_more.visibility = View.GONE
        val mOrdersLoadMore : List<DataGuest> = message
        if (mOrdersLoadMore.size > 0){
            if (message.size >= MAX_SIZE){
                adapter!!.setMoreDataAvailable(true)
            }else{
                adapter!!.setMoreDataAvailable(false)
            }
            mModule.addAll(mOrdersLoadMore)
        }else{
            adapter!!.setMoreDataAvailable(false)
        }
        adapter!!.notifyDataChanged()
    }

    override fun onLoadMoreFailedGuest(message: String) {
        progress_module_more.visibility = View.GONE
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
