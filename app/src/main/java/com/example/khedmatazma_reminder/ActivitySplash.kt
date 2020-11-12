package com.example.khedmatazma_reminder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.khedmatazma_reminder.login.ActivityRegister
import com.example.khedmatazma_reminder.tasks.ActivityTasks


class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val thread: Thread = object : Thread() {
            override fun run() {
                sleep(2000)
                if(G.LoggedInId > 0 ){
                    startActivity(Intent(baseContext , ActivityTasks::class.java))
                }else{
                    val i = Intent(baseContext, ActivityRegister::class.java)
                    startActivity(i)
                }
                finish()
            }
        }
        thread.start()
    }
}