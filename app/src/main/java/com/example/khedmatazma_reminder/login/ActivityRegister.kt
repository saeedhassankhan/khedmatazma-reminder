package com.example.khedmatazma_reminder.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.khedmatazma_reminder.*
import com.example.khedmatazma_reminder.login.Validator.Companion.CORRECT
import com.example.khedmatazma_reminder.tasks.ActivityTasks
import kotlinx.android.synthetic.main.activity_register.*


class ActivityRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener(){
            validate()
        }

        btnGoToSign.setOnClickListener(){
            startActivity(Intent(baseContext , ActivityLogin::class.java))
            finish()
        }
    }

    fun validate(){
        var pass = edtPassword.text.toString()
        var userName = edtUserName.text.toString()
        var phoneNumber = edtPhoneNumber.text.toString()

        var validation = Validator()
        var toast = CToast()

        var vPass = validation.pass(pass);
        var vPhoneNumber = validation.phone(phoneNumber)
        var vUserName = validation.userName(userName)

        if(vUserName != CORRECT){
            toast.show(baseContext , vUserName)
        }else if(vPhoneNumber != CORRECT){
            toast.show(baseContext , vPhoneNumber)
        }else if(validation.pass(pass) != CORRECT){
            toast.show(baseContext , vPass)
        }else{
            register(userName , phoneNumber , pass)
        }
    }


    fun register(userName : String , phoneNumber:String , pass : String){
        var db = DatabaseManager().setDbAddress(G.DIR_APP!!)
        var insertedId = db.insertUser(userName , phoneNumber , pass)
        G.setLogedInId(insertedId)
        startActivity(Intent(baseContext , ActivityTasks::class.java))
        finish()
    }
}