package com.example.khedmatazma_reminder

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.File


class DatabaseManager {

    companion object{
        val TABLE_USERS = "users"
        val TABLE_TASKS = "tasks"
        val DB_NAME = "khedmatazma_reminder";
    }

    private var dbAddress = G.DIR_APP

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


    /**
     * return id of added item
     */
    fun insertUser(
        username : String ,
        phoneNumber : String ,
        password : String

    ): Int {
        val database =
            SQLiteDatabase.openOrCreateDatabase("$dbAddress/$DB_NAME", null)
        //dont forget to put boolean's  in '' => 'value'
        database.execSQL(
            "INSERT INTO " + TABLE_USERS + " (username ,phone_number,password)" +
                    " VALUES ('" + username + "','" + phoneNumber + "','" + password + "')")


        val id = getLastRowId(database , TABLE_USERS)

        database.close()

        return id;

    }

    public fun searchUser(phone : String , password: String) : String{
        val db =
            SQLiteDatabase.openOrCreateDatabase("$dbAddress/$DB_NAME", null)

        var cursor : Cursor
         cursor = db.rawQuery(
            "SELECT * FROM " + TABLE_USERS + " WHERE "
                     + "phone_number = '" + phone + "' AND password =  '" + password +  "'" , null
        )

        var id = "0"
        if(cursor.count > 0){
            cursor.moveToLast()
            id = cursor.getString(cursor.getColumnIndex(TABLE_USERS + "_id"))
        }

        Log.i("I" , cursor.toString())

        return id

    }

    private fun getLastRowId(database: SQLiteDatabase, tbName: String): Int {
        val cursor: Cursor
        cursor = database.rawQuery(
            "SELECT * FROM " + tbName + " ORDER BY " + tbName + "_id" + " DESC LIMIT 1",
            null
        )
        var id = 0
        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(tbName + "_id"))
        }
        return id
    }
}