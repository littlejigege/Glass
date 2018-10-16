package com.qgstudio.glass.contact

import com.qgstudio.glass.model.data.Contact
import com.qgstudio.glass.model.data.ServerResponse
import io.reactivex.Observable
import retrofit2.http.Headers
import retrofit2.http.POST

interface ContactApi {
    @POST("contact/add")
    @Headers("Content-Type:application/json")
    fun addContact(contact: Contact): Observable<ServerResponse<Any?>>

    @POST("contact/delete")
    @Headers("Content-Type:application/json")
    fun removeContact(any:Any): Observable<ServerResponse<Any?>>

    @POST("contact/show")
    @Headers("Content-Type:application/json")
    fun getAllContacts(): Observable<ServerResponse<List<Contact>>>
}