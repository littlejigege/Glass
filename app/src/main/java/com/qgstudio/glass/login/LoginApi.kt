package com.qgstudio.glass.login

import com.qgstudio.glass.model.data.ServerResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @POST("login/request")
    @Headers("Content-Type:application/json")
    fun requestVeCode(@Body any: Any): Observable<ServerResponse<Any?>>

    @POST("login/check")
    @Headers("Content-Type:application/json")
    fun login(@Body any: Any): Observable<ServerResponse<Any?>>
}