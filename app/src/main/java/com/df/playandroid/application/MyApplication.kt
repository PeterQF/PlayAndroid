package com.df.playandroid.application

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
    }
}