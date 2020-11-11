package com.example.khedmatazma_reminder

import android.database.sqlite.SQLiteDatabase
import java.io.File

class DatabaseManager {

    //*************situations***********************
    val FIRST_CREATED = 0
    private val changePermission = true
    val TABLE_USERS = "users"
    val TABLE_TASKS = "tasks"

    //**********************************************
    //var listener: DatabaseManager.OnDatabaseListener? = null
    /*fun setListener(listener: global.DatabaseManager.OnDatabaseListener?): DatabaseManager? {
        this.listener = listener
        return this
    }*/

    var database: SQLiteDatabase? = null

    fun createDb(
        dirDatabase: String,
        dataBaseName: String
    ): Boolean {
        File(dirDatabase).mkdirs()
        val file = File("$dirDatabase/$dataBaseName")
        // file.delete();
        if (!file.exists()) {
            database = SQLiteDatabase.openOrCreateDatabase("$dirDatabase/$dataBaseName", null)


            database?.execSQL(
                "CREATE  TABLE " + TABLE_USERS +
                        "(" + TABLE_USERS + "_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE " +
                        ",phone_number STRING" +
                        ",password STRING" +
                        ",username STRING" +
                        ")"
            )

            database?.execSQL(
                "CREATE  TABLE " + TABLE_TASKS +
                        "(" + TABLE_TASKS + "_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE " +
                        ",title STRING" +
                        ",description STRING" +
                        ",fk_user STRING"+
                        ")"
            )

            database?.close()

            return true
        }
        return false
    }
}