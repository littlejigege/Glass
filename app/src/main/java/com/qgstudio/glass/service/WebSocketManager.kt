package com.qgstudio.glass.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qgstudio.glass.common.model.data.Record
import com.qgstudio.glass.common.model.data.ServerResponse
import com.qgstudio.glass.showToast
import okhttp3.*
import org.greenrobot.eventbus.EventBus

class WebSocketManager private constructor() : WebSocketListener() {

    private var mWebSocket: WebSocket? = null
    private val httpClient by lazy { OkHttpClient.Builder().build() }

    companion object {
        //与服务器建立连接
        fun connect(url: String): WebSocketManager {
            val manager = WebSocketManager()
            if (manager.mWebSocket == null) {
                val request = Request.Builder()
                        .url(url)
                        .build()
                manager.httpClient.newWebSocket(request, manager)
            }
            return manager
        }
    }


    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        mWebSocket = webSocket
        Log.e("webSocket", "onOpen")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        showToast("长链接错误：${t.message}")
        Log.e("webSocket", "onFailure")
    }

    //只接受坐标json，其他信息在转换json过程中丢失抛出异常
    override fun onMessage(webSocket: WebSocket?, text: String) {
        Log.e("webSocket", "onMessage:$text")
        try {
            //尝试将收到的信息当成Record下发
            EventBus.getDefault()
                    .post(
                            (
                                    Gson().fromJson(
                                            text, object : TypeToken<ServerResponse<Record>>() {}.type) as ServerResponse<Record>
                                    )
                                    .data
                    )
        } catch (e: Exception) {
            Log.e("webSocketError", "onMessage:${e.message}")
        }
    }


    override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        mWebSocket = null
        Log.e("webSocket", "onClosed")
    }

    //向服务器发送信息
    fun send(any: Any) {
        mWebSocket?.let {
            val text = Gson().toJson(any)
            it.send(text)
        }
    }

}