package com.example.khedmatazma_reminder.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.khedmatazma_reminder.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSign.setOnClickListener(){
            Toast.makeText(baseContext , "clicked" , Toast.LENGTH_SHORT).show()
        }
    }
}