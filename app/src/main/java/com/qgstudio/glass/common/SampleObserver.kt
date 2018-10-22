package com.qgstudio.glass.common

import com.qgstudio.glass.common.model.data.ServerResponse
import com.qgstudio.glass.showToast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class SampleObserver<T> : Observer<T> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        if (t is ServerResponse<*>) {
            showToast(t.getStateCodeEnum().info)
        }
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

}