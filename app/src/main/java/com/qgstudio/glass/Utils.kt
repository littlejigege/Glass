package com.qgstudio.glass

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.qgstudio.glass.model.data.ServerResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


object MapBuilder {
    class PairsBuilder {
        var pairs: MutableMap<String, Any> = HashMap()
        operator fun String.minus(value: Any) {
            pairs[this] = value
        }
    }

    fun build(makePairs: PairsBuilder.() -> Unit): Map<String, Any> {
        val pairsBuilder = PairsBuilder()
        pairsBuilder.makePairs()
        return pairsBuilder.pairs
    }

}

object O {
    val mainThreadHandler: Handler by lazy { Handler(Looper.getMainLooper()) }//主线程
    var mToast: Toast? = null
    var mMsg: Any? = null
}

fun Any.inUiThread(run: () -> Unit) = O.mainThreadHandler.post(run)

fun Any.showToast(msg: Any) {

    inUiThread {
        if (null == O.mToast || msg.toString() !== O.mMsg!!.toString()) {

            if (null == O.mToast) {
                O.mToast = Toast.makeText(App.instance, msg.toString(), Toast.LENGTH_SHORT)
            } else {
                O.mToast!!.setText(msg.toString())
            }
            O.mMsg = msg
        }
        O.mToast?.show()
    }
}

//封装了线程调度，只关注onNext
@SuppressLint("CheckResult")
fun <T : Any> Observable<T>.normalSubscribeBy(onNext: (T) -> Unit): Disposable {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                    onError = {
                        it.printStackTrace()
                        showToast("网络错误：${it.message}")
                    },
                    onNext = {
                        if (it is ServerResponse<*>) {
                            showToast(it.getStateCodeEnum().info)
                        }
                        onNext(it)
                    }
            )
}