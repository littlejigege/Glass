package com.qgstudio.glass.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import com.qgstudio.glass.R
import com.qgstudio.glass.common.model.data.Record
import com.qgstudio.glass.common.model.db.AppDatabase
import com.qgstudio.glass.map.MyMapActivity
import com.qgstudio.glass.showToast
import com.qgstudio.glass.spothistory.SpotHistoryFragment
import com.qmuiteam.qmui.span.QMUIAlignMiddleImageSpan
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIDrawableHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.tencent.tencentmap.mapsdk.map.MapActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import java.util.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupButton()
        setupTabSegment()

    }

    private fun setupTabSegment() {
        //viewPager
        viewPager.adapter = HomeFragmentAdapter(supportFragmentManager).apply {
            addPage(SpotHistoryFragment::class.java)
            addPage(SpotHistoryFragment::class.java)
        }
        //tab
        tabSegment.addTab(QMUITabSegment.Tab("历史轨迹记录"))
                .addTab(QMUITabSegment.Tab("报警记录"))
                .mode = QMUITabSegment.MODE_FIXED
        tabSegment.setHasIndicator(true)
        tabSegment.setupWithViewPager(viewPager, false)
    }

    private fun setupButton() {
        val spanWidthCharacterCount = 2f
        val spannable = SpannableString("[icon]进入地图查看实时轨迹")
        val iconDrawable = resources.getDrawable(R.drawable.ic_locate)
        iconDrawable?.setBounds(0, 0, iconDrawable.intrinsicWidth, iconDrawable.intrinsicHeight)
        val alignMiddleImageSpan = QMUIAlignMiddleImageSpan(iconDrawable, QMUIAlignMiddleImageSpan.ALIGN_MIDDLE, spanWidthCharacterCount)
        spannable.setSpan(alignMiddleImageSpan, 0, "[icon]".length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        buttonToMap.text = spannable
        buttonToMap.setOnClickListener {
            startActivity(Intent(this, MyMapActivity::class.java))
        }
    }


}
