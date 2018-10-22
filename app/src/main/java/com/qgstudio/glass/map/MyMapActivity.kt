package com.qgstudio.glass.map

import android.os.Bundle
import com.qgstudio.glass.R
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.tencent.tencentmap.mapsdk.map.MapActivity

class MyMapActivity : MapActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        QMUIStatusBarHelper.translucent(this)//沉浸
        QMUIStatusBarHelper.setStatusBarLightMode(this)//黑字
    }
}
