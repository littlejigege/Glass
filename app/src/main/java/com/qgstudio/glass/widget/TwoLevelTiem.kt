package com.qgstudio.glass.widget

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.LinearLayout

class TwoLevelTiem(context: Context?) : LinearLayout(context) {
    private var mParentView: View? = null
    private var mFooterView: View? = null

    fun setParentView(@LayoutRes id: Int) {
        val parentView = View.inflate(context, id, null)
        //非空,先去掉原本的
        if (mParentView != null) {
            removeView(mParentView)
        }
        addView(parentView, 0).also { mParentView = parentView }
    }
}