package com.example.khedmatazma_reminder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.khedmatazma_reminder.login.ActivityLogin


class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val thread: Thread = object : Thread() {
            override fun run() {
                sleep(2000)
                val i = Intent(baseContext, ActivityLogin::class.java)
                startActivity(i)
                finish()
            }
        }
        thread.start()
    }
}