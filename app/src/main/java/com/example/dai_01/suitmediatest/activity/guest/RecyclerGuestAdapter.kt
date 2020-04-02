package com.example.dai_01.suitmediatest.activity.guest

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.extension.inflate
import com.example.dai_01.suitmediatest.model.DataGuest

class RecyclerGuestAdapter (private val activity: GuestActivity, private val items:List<DataGuest>) : RecyclerView.Adapter<RecyclerGuestAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerGuestAdapter.ViewHolder {
        val inflatedView= parent!!.inflate(R.layout.item_guest,false)
        return RecyclerGuestAdapter.ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerGuestAdapter.ViewHolder?, position: Int) {
        val view=holder?.itemView
        val data=items[position]

        view?.let {

            it.visibility= View.VISIBLE
            (it.findViewById(R.id.nameGuest) as TextView).text=data.name
            (it.findViewById(R.id.birthday) as TextView).text=data.birthdate

        }

        view?.setOnClickListener {
            val dataIntent = Intent()

            val arrDate = data.birthdate.split("-")
            var date = arrDate[2].toInt()
            var stringToast = ""

            if (date%2==0 && date%3==0){
                stringToast = "IOS"
            }else if (date%2==0){
                stringToast = "blackberry"
            }else{
                stringToast = "Android"
            }

            dataIntent.putExtra("toast",stringToast)
            dataIntent.putExtra("name_selected",data.name)
            activity.setResult(Activity.RESULT_OK,dataIntent)
            activity.finish()
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

    }
}