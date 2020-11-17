package com.example.khedmatazma_reminder.utilities

import com.example.khedmatazma_reminder.G

class CPrefrence {
    fun save(key: String?, value: Int) {
        val editor = G.preferences.edit()
        editor.putInt(key, value)
        editor.commit()

    }

    fun save(key: String?, value: String?) {
        val editor = G.preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun save(key: String?, value: Boolean) {
        val editor = G.preferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun save(key: String?, value: Float) {
        val editor = G.preferences.edit()
        editor.putFloat(key, value)
        editor.commit()
    }
}