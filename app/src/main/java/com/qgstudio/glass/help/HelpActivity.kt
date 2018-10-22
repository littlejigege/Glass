package com.qgstudio.glass.help

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Window
import com.qgstudio.glass.R
import com.qmuiteam.qmui.span.QMUIAlignMiddleImageSpan
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        setupButton()

    }

    private fun setupButton(){
        val spanWidthCharacterCount = 2f
        val spannable = SpannableString("[icon]进入地图查看详情")
        val iconDrawable = resources.getDrawable(R.drawable.ic_locate)
        iconDrawable?.setBounds(0, 0, iconDrawable.intrinsicWidth, iconDrawable.intrinsicHeight)
        val alignMiddleImageSpan = QMUIAlignMiddleImageSpan(iconDrawable, QMUIAlignMiddleImageSpan.ALIGN_MIDDLE, spanWidthCharacterCount)
        spannable.setSpan(alignMiddleImageSpan, 0, "[icon]".length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        buttonToMap.text = spannable
    }
}
