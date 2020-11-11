package com.example.khedmatazma_reminder

import android.app.Application
import java.io.File

class G : Application(){
    companion object{
        val DB_NAME = "khedmatazma_reminder";
        var DIR_APP: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        DIR_APP = getExternalFilesDir(null)!!.absolutePath


        //startService(new Intent(this , StickyService.class));
        File(DIR_APP).mkdirs()
        val databaseManager = DatabaseManager()
        databaseManager.createDb(DIR_APP!! , DB_NAME)
    }
}