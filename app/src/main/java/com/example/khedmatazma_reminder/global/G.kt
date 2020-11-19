package com.example.khedmatazma_reminder.global

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.khedmatazma_reminder.utilities.CPrefrences
import com.example.khedmatazma_reminder.utilities.DatabaseManager
import java.io.File

class G : Application(){
    companion object{
        var DIR_APP: String? = null
        lateinit  var  preferences : SharedPreferences;
        var LoggedInId = -1


         fun setLogedInId(id : Int){
             CPrefrences.save(GLOBAL_VALUES.LOGGED_IN_USER_ID, id)
             LoggedInId = id
        }

        fun getLogedInId() : Int{
            return LoggedInId
        }
    }

    override fun onCreate() {
        super.onCreate()
        DIR_APP = getExternalFilesDir(null)!!.absolutePath

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        LoggedInId = preferences.getInt(
            GLOBAL_VALUES.LOGGED_IN_USER_ID, -1)


        //startService(new Intent(this , StickyService.class));
        File(DIR_APP).mkdirs()

        val databaseManager = DatabaseManager().setDbAddress(DIR_APP!!)
        databaseManager.createDb()

    }


}