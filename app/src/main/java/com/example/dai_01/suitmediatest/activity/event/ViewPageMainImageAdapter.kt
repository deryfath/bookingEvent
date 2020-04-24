package com.example.dai_01.suitmediatest.activity.event

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.model.DataEvent


class ViewPageMainImageAdapter(val context: Context, val layouts: MutableList<DataEvent>) : PagerAdapter() {

    var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater!!.inflate(R.layout.item_event, container, false)
        val data = layouts[position]

        var textImg = view.findViewById(R.id.nameEvent) as TextView
        var textDateImg = view.findViewById(R.id.dateEvent) as TextView
        var imgView = view.findViewById(R.id.imageEvent) as ImageView

        textImg.setText(data.name)
        textDateImg.setText("23 April 2020")

        Glide.with(context).load(data.image).into(imgView)


        view.setOnClickListener {
            println("CLICK : ${layouts[position]}")


        }

        container.addView(view)

        return view
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}