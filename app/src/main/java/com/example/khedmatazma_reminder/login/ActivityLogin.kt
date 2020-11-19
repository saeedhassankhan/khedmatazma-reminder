package com.example.khedmatazma_reminder.login

import android.content.Intent
import android.os.Bundle
import com.example.khedmatazma_reminder.global.G
import com.example.khedmatazma_reminder.R
import com.example.khedmatazma_reminder.tasks.ActivityTasks
import com.example.khedmatazma_reminder.BaseAppCompactActivity
import com.example.khedmatazma_reminder.utilities.CHash
import com.example.khedmatazma_reminder.utilities.CToast
import com.example.khedmatazma_reminder.utilities.DatabaseManager
import kotlinx.android.synthetic.main.activity_login.*


class ActivityLogin : BaseAppCompactActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         var cHash = CHash()

        btnSign.setOnClickListener{
            var phoneNumber = edtPhoneNumber.text.toString()
            var password = cHash.md5(edtPassword.text.toString())

            checkInputs(phoneNumber , password)
        }


        btnGoToRegister.setOnClickListener{
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