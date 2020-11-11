package com.example.khedmatazma_reminder

import android.app.Application
import com.example.khedmatazma_reminder.DatabaseManager.Companion.DB_NAME
import com.example.khedmatazma_reminder.DatabaseManager.Companion.TABLE_USERS
import java.io.File

class G : Application(){
    companion object{
        var DIR_APP: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        DIR_APP = getExternalFilesDir(null)!!.absolutePath


        //startService(new Intent(this , StickyService.class));
        File(DIR_APP).mkdirs()

        val databaseManager = DatabaseManager().setDbAddress(DIR_APP!!)

        databaseManager.createDb()


        for (i in 1..5){
            databaseManager.insertUser( "user" + i , "phone" + i, "pass" + i);
        }
    }
}