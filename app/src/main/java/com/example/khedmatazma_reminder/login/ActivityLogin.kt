package com.example.khedmatazma_reminder.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.khedmatazma_reminder.*
import com.example.khedmatazma_reminder.tasks.ActivityTasks
import kotlinx.android.synthetic.main.activity_login.*


class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSign.setOnClickListener(){
            var phoneNumber = edtPhoneNumber.text.toString()
            var password = edtPassword.text.toString()

            checkInputs(phoneNumber , password);
        }
        btnGoToRegister.setOnClickListener(){
            startActivity(Intent(baseContext , ActivityRegister::class.java))
            finish()
        }


    }

    private fun checkInputs(phoneNumber : String , password : String){

        var db = DatabaseManager()
        var id = db.searchUser(phoneNumber , password)

        if(id != "0") {
            G.setLogedInId(id.toInt())

            startActivity(Intent(baseContext, ActivityTasks::class.java))

            finish()
        }else{
            CToast().show(baseContext , "رمز عبور یا شماره تلفن اشتباه است!")
        }
    }
}