package com.example.khedmatazma_reminder

import android.content.Context
import android.widget.Toast

class CToast {
    public fun show(context : Context, passage : String){
        Toast.makeText(context , passage , Toast.LENGTH_SHORT).show()
    }
}