package com.df.playandroid.main.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.df.playandroid.R
import com.df.playandroid.utils.LogUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
