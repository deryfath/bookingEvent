package com.example.dai_01.suitmediatest.activity.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        btnCheck.setOnClickListener {
            if (isPalindrome(inputPalindrome.text.toString())){
                Toast.makeText(getApplicationContext(),"PALINDROME", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext(),"NOT PALINDROME", Toast.LENGTH_SHORT).show();

            }
        }

    }

    fun isPalindrome(str: String): Boolean {

        var i = 0
        var j = str.length - 1

        while (i < j) {

            if (str[i] != str[j])
                return false

            i++
            j--
        }
        return true
    }
}
