package com.example.khedmatazma_reminder

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.io.File

class G : Application(){
    companion object{
        var DIR_APP: String? = null
        lateinit  var  preferences : SharedPreferences ;
    }

    override fun onCreate() {
        super.onCreate()
        DIR_APP = getExternalFilesDir(null)!!.absolutePath

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        //startService(new Intent(this , StickyService.class));
        File(DIR_APP).mkdirs()

        val databaseManager = DatabaseManager().setDbAddress(DIR_APP!!)

        databaseManager.createDb()

    }
}