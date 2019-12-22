package com.df.playandroid.http

import com.df.playandroid.config.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofit : BaseRetrofit() {

    val api: ApiServer by lazy {
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(ApiServer::class.java)
    }

    companion object {
        val instance by lazy { InstanceHolder.instance }
    }

    private object InstanceHolder {
        val instance = ApiRetrofit()
    }
}