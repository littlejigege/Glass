package com.qgstudio.glass.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qgstudio.glass.R
import com.qmuiteam.qmui.widget.QMUITabSegment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        tabSegment.addTab(QMUITabSegment.Tab("1"))
                .addTab(QMUITabSegment.Tab("2"))
                .mode = QMUITabSegment.MODE_FIXED
    }
}
