package com.df.playandroid.application

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    
    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }
}