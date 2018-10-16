package com.qgstudio.glass.common

import com.qgstudio.glass.login.LoginApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client by lazy {
        Retrofit
                .Builder()
                .baseUrl("http://1.1.1.1:8080/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    val loginApi: LoginApi by lazy { client.create(LoginApi::class.java) }//登录api服务

}