package com.example.dai_01.suitmediatest.activity.guest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.extension.inflate
import com.example.dai_01.suitmediatest.model.DataGuest

class RecyclerGuestAdapter (val activity: GuestActivity, val mData:List<DataGuest>, val itemClick: (DataGuest)-> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_REQUEST = 0
    val TYPE_LOAD = 1
    var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading: Boolean = false
    var isMoreDataAvailable : Boolean? = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_REQUEST) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
            return ItemHolder(view, itemClick)
        }else if (viewType == TYPE_LOAD){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            return FooterViewHolder(view)
        }
        return null!!
    }

    override fun getItemCount(): Int{
        if (mData == null){
            return 0
        }else{
            return mData.size
        }
    }

    fun isPositionFooter(position: Int): Boolean{
        return position == mData.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isPositionFooter(position)){
            return TYPE_LOAD
        }else{
            return TYPE_REQUEST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position >= itemCount -1 && isMoreDataAvailable!! && !isLoading && onLoadMoreListener != null){
            isLoading = true
            onLoadMoreListener!!.onLoadMore()
        }

        if (holder is ItemHolder){
            holder.bindDataNotif(activity,mData[position])
        }
    }

    class ItemHolder(view: View, val itemClick: (DataGuest) -> Unit): RecyclerView.ViewHolder(view) {
        var stringToast = ""

        fun bindDataNotif(activity: GuestActivity, dataGuest: DataGuest){
            with(dataGuest){
                Glide.with(itemView.context).load(dataGuest.avatar).into(itemView.findViewById(R.id.iv_guest) as ImageView)
                (itemView.findViewById(R.id.nameGuest) as TextView).text=dataGuest.first_name+" "+dataGuest.last_name


                itemView.setOnClickListener {
                    val dataIntent = Intent()

                    if (dataGuest.id%2==0 && dataGuest.id%3==0){
                        stringToast = "IOS"
                    }else if (dataGuest.id%2==0){
                        stringToast = "blackberry"
                    }else{
                        stringToast = "Android"
                    }

                    dataIntent.putExtra("toast",stringToast)
                    dataIntent.putExtra("name_selected",dataGuest.first_name+" "+dataGuest.last_name)
                    activity.setResult(Activity.RESULT_OK,dataIntent)
                    activity.finish()
                }
            }

        }

    }

    inner class FooterViewHolder(var view: View) : RecyclerView.ViewHolder(view){
    }

    fun setLoadMoreListener(onLoadMoreListener: OnLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun setMoreDataAvailable(moreDataAvailable: Boolean) {
        isMoreDataAvailable = moreDataAvailable
    }

    fun notifyDataChanged() {
        notifyDataSetChanged()
        isLoading = false
    }

    interface OnLoadMoreListener {

        fun onLoadMore()
    }
}