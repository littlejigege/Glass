package com.qgstudio.glass.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.qgstudio.glass.MapBuilder
import com.qgstudio.glass.common.RetrofitClient
import com.qgstudio.glass.common.SampleObserver
import com.qgstudio.glass.normalSubscribeBy
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel : ViewModel() {
    private val loginServerResponse by lazy { MutableLiveData<Int>() }
    fun requestVeCode(phone: String) {
        RetrofitClient
                .loginApi
                .requestVeCode(MapBuilder.build { "phone" - phone })
                .normalSubscribeBy {}
    }

    fun login(phone: String, veCode: String): LiveData<Int> {
        RetrofitClient
                .loginApi
                .login(MapBuilder.build {
                    "phone" - phone
                    "authCode" - veCode
                })
                .normalSubscribeBy {
                    loginServerResponse.postValue(it.state)
                }
        return loginServerResponse
    }
}