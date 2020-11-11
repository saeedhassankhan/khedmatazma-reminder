package com.example.khedmatazma_reminder.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.khedmatazma_reminder.CToast
import com.example.khedmatazma_reminder.R
import com.example.khedmatazma_reminder.login.Validator.Companion.CORRECT
import kotlinx.android.synthetic.main.activity_register.*


class ActivityRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)





        btnRegister.setOnClickListener(){
            validate()


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
        }
    }
}