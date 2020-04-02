package com.example.dai_01.suitmediatest.activity.event

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.extension.inflate
import com.example.dai_01.suitmediatest.model.DataEvent
import javax.xml.transform.Result

class RecyclerEventAdapter (private val activity: EventActivity, private val items:List<DataEvent>) : RecyclerView.Adapter<RecyclerEventAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerEventAdapter.ViewHolder {
        val inflatedView= parent!!.inflate(R.layout.item_event,false)
        return RecyclerEventAdapter.ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerEventAdapter.ViewHolder?, position: Int) {
        val view=holder?.itemView
        val data=items[position]

        view?.let {

            it.visibility= View.VISIBLE
            Glide.with(it.context).load(data.image).into(it.findViewById(R.id.imageEvent) as ImageView)
            (it.findViewById(R.id.nameEvent) as TextView).text=data.name
            (it.findViewById(R.id.dateEvent) as TextView).text=data.date

        }

        view?.setOnClickListener {
            val dataIntent = Intent()
            dataIntent.putExtra("name_selected",data.name)
            activity.setResult(Activity.RESULT_OK,dataIntent)
            activity.finish()
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

    }
}