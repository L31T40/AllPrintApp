package com.example.allprintapp.ui.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPref {
    private var mSharedPref: SharedPreferences? = null
    const val KEY_USER = "KEY_USER"
    const val KEY_NUMFUNC = "KEY_NUMFUNC"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_EMAIL = "KEY_EMAIL"
    const val KEY_TEL = "KEY_TEL"
    const val KEY_LOCAL = "KEY_LOCAL"
    const val KEY_FOTO = "KEY_FOTO"
    const val KEY_ISLOGGED = false
    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun readStr(key: String?, defValue: String?): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun writeStr(key: String?, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun readBoolean(key: String?, defValue: Boolean): Boolean {
        return mSharedPref!!.getBoolean(key, defValue)
    }

    fun writeBoolean(key: String?, value: Boolean) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    fun readInt(key: String?, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun writeInt(key: String?, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!).apply()
    }
}