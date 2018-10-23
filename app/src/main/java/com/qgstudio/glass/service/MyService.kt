package com.qgstudio.glass.service

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.qgstudio.glass.R
import com.qgstudio.glass.common.RetrofitClient
import com.qgstudio.glass.common.model.data.Record
import com.qgstudio.glass.common.model.db.AppDatabase
import com.qgstudio.glass.help.HelpActivity
import com.qgstudio.glass.home.HomeActivity
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 负责与服务器长连接的服务，同时是app的前台服务
 */
class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val websocketURL: String
        get() {
            val httpUrl = RetrofitClient.client.baseUrl()
            val ip = httpUrl.host()
            val port = httpUrl.port()
            return "ws://$ip:$port/ws?gid=1"
        }

    private val CHANNEL_ID = "MyService"
    private val NAME = "Glass"
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }


    override fun onCreate() {
        super.onCreate()
        //注册总线，监听信息
        EventBus.getDefault().register(this)
        //构建notification通道
        buildChannel()
        //前台服务保活法
        toForeground()
        //与服务器连接，内部自动监听、下发信息
        openWebSocket()

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 安卓o通知需要构造channel
     */
    @TargetApi(Build.VERSION_CODES.O)
    private fun buildChannel() {
        val channel = NotificationChannel(CHANNEL_ID, NAME, NotificationManager.IMPORTANCE_HIGH)
        with(channel) {
            description = "智能避障设备"
            enableLights(true)
            enableVibration(true)
            //TODO 设置音效
        }
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * 把服务设置为前台服务
     */
    private fun toForeground() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build()
        startForeground(1, notification)
    }

    private fun launchHelpNotification() {
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_locate)
                .setContentTitle("收到报警信息！！")
                .setShowWhen(true)
                .setContentText("你的关注对象发出报警信息，请速查看")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        notificationManager.notify(2, mBuilder.build())

    }

    private fun openWebSocket() {
        WebSocketManager.connect(websocketURL)
    }

    @Subscribe
    fun onRecord(record: Record) {
        //发出通知
        launchHelpNotification()
        //插入数据库
        GlobalScope.launch {
            AppDatabase.getInstance(this@MyService)
                    .recordDao()
                    .insert(record)
        }
//TODO 传数据
        startActivity(Intent(this, HelpActivity::class.java))
    }

}

