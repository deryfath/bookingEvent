package com.example.dai_01.suitmediatest.activity.main

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dai_01.suitmediatest.App
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.activity.event.EventActivity
import com.example.dai_01.suitmediatest.activity.guest.GuestActivity
import com.example.dai_01.suitmediatest.model.ProductRequest
import com.example.dai_01.suitmediatest.model.ProductResponse
import com.google.gson.GsonBuilder
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject




class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_EVENT = 1234;
    private val REQUEST_CODE_GUEST = 4556;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val name = intent.getStringExtra("name");

        nameLogin.text = name

        btnEvent.setOnClickListener {
            startActivityForResult(Intent(this,EventActivity::class.java),REQUEST_CODE_EVENT)
        }

        btnGuest.setOnClickListener {
            startActivityForResult(Intent(this,GuestActivity::class.java),REQUEST_CODE_GUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQUEST_CODE_EVENT && resultCode== Activity.RESULT_OK){
            btnEvent.text = data?.getStringExtra("name_selected")
        }

        if (requestCode==REQUEST_CODE_GUEST && resultCode== Activity.RESULT_OK){
            btnGuest.text = data?.getStringExtra("name_selected")
            val toastText = data?.getStringExtra("toast")

            Toast.makeText(getApplicationContext(),toastText,Toast.LENGTH_SHORT).show();

        }

    }



}
