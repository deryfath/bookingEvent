package com.example.dai_01.suitmediatest.activity.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dai_01.suitmediatest.R
import com.example.dai_01.suitmediatest.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnNext.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("name", inputName.text.toString());
            startActivity(intent)

        }

    }
}
