package com.example.khedmatazma_reminder.login

class Validator {

    companion object{
        var CORRECT = "correct"
    }


    public fun pass(input : String) : String{
        if(input.isEmpty() ){
            return "پسورد الزامیست"
        }else if(input.length < 5){
            return "پسورد کوتاه است"
        }
        return CORRECT
    }

    public fun phone(input: String) : String{
        if(input.isEmpty() ){
            return "شماره موبایل  الزامیست"
        }else if(input.length < 9){
            return "شماره موبایل اشتباه است"
        }
        return CORRECT
    }

    public fun userName(input : String) :String{
        if(input.isEmpty()){
            return "نام کاربری الزامیست"
        }else if(input.length < 5){
            return "نام کاربری کوتاه است"
        }
        return CORRECT
    }
}