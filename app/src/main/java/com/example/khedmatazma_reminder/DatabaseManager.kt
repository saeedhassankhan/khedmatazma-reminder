package com.example.khedmatazma_reminder

import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract
import java.io.File

class DatabaseManager {

    companion object{
        val TABLE_USERS = "users"
        val TABLE_TASKS = "tasks"
        val DB_NAME = "khedmatazma_reminder";
    }

    private var dbAddress = ""

    public fun setDbAddress(address : String ) : DatabaseManager{
        dbAddress = address
        return this
    }



    //*************situations***********************
    val FIRST_CREATED = 0


    //**********************************************
    //var listener: DatabaseManager.OnDatabaseListener? = null
    /*fun setListener(listener: global.DatabaseManager.OnDatabaseListener?): DatabaseManager? {
        this.listener = listener
        return this
    }*/

    var database: SQLiteDatabase? = null

    fun createDb(): Boolean {
        File(dbAddress).mkdirs()
        val file = File("$dbAddress/$DB_NAME")
        // file.delete();
        if (!file.exists()) {
            database = SQLiteDatabase.openOrCreateDatabase("$dbAddress/$DB_NAME", null)

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


    fun insertUser(
        username : String ,
        phoneNumber : String ,
        password : String

    ): Boolean {
        val database =
            SQLiteDatabase.openOrCreateDatabase("$dbAddress/$DB_NAME", null)
        //dont forget to put boolean's  in '' => 'value'
        database.execSQL(
            "INSERT INTO " + TABLE_USERS + " (username ,phone_number,password)" +
                    " VALUES ('" + username + "','" + phoneNumber + "','" + password + "')")

        database.close()
        return true
    }
}