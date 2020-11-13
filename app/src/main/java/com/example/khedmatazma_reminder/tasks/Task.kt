package com.example.khedmatazma_reminder.tasks

class Task {
    var title : String = ""
    var description : String = ""
    var id : Int = 0
    var fk_user = 0
    var date = ""
    var time = ""


    fun getHour() : String{
        if(time.isNotEmpty()){
            return time.split(":")[0]
        }
        return "-1"
    }
    fun getMinute():String{
        if(time.isNotEmpty()){
            return time.split(":")[1]
        }
        return "-1"
    }


    fun getYear():String{
        if(date.isNotEmpty()){
            return date.split("/")[0]
        }
        return "-1"
    }

    fun getMonth() : String{
        if(date.isNotEmpty()){
            return date.split("/")[1]
        }
        return "-1"
    }

    fun getDay() : String{
        if(date.isNotEmpty()){
            return date.split("/")[2]
        }
        return "-1"

    }


}