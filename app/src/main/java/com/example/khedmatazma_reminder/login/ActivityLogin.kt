package com.example.khedmatazma_reminder.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.khedmatazma_reminder.*
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
            Prefrences.save(GLOBAL_VALUES.LOGGED_IN_USER_ID, id.toInt())

            startActivity(Intent(baseContext, ActivityTasks::class.java))

            finish()
        }else{
            CToast().show(baseContext , "رمز عبور یا شماره تلفن اشتباه است!")
        }
    }
}