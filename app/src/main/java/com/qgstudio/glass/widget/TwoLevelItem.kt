package com.qgstudio.glass.widget

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.qgstudio.glass.R
import com.qgstudio.glass.common.model.data.Record
import com.qgstudio.glass.dp2px
import com.qgstudio.glass.showToast
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import kotlinx.android.synthetic.main.item_record.view.*

class TwoLevelItem : LinearLayout, View.OnClickListener {


    private var mParentView: View? = null
    private val mFooterView by lazy { View.inflate(context, R.layout.item_footer, null) }
    private val mEmptyView by lazy { View.inflate(context, R.layout.item_empty, null) }
    private var _onParentClick: (view: View) -> Unit = { }
    private var _onFooterClick: (view: View) -> Unit = { }
    private var _onChildClick: (pos: Int, view: View) -> Unit = { _: Int, _: View -> }
    @LayoutRes
    private var childLayoutID = R.layout.item_record//子item的资源id
    var isExpanded = false//子item是否展开
    var records: List<Record>? = null//数据
    fun onParentClick(opc: (view: View) -> Unit) {
        _onParentClick = opc
    }

    fun onChildClick(occ: (pos: Int, view: View) -> Unit) {
        _onChildClick = occ
    }

    fun onFooterClick(ofc: (view: View) -> Unit) {
        _onFooterClick = ofc
    }

    private fun init() {
        orientation = VERTICAL
        setParentView(R.layout.item_day)
        mFooterView.setOnClickListener(this)
        addChilds(null)
    }

    private fun setParentView(@LayoutRes id: Int) {
        val parentView = View.inflate(context, id, null)
        //非空,先去掉原本的
        if (mParentView != null) {
            removeView(mParentView)
        }
        addView(parentView, 0).also { mParentView = parentView }
        parentView.setOnClickListener(this)
    }


    private fun addFooterView() {

    }

    /**
     * itemHeight dp
     */
    fun addChilds(records: List<Record>?) {
        var isFirstTime = true
        if (childCount > 2) {
            //初始化过的,footerView存在
            isFirstTime = false
            removeViews(1, childCount - 2)
        }

        if (records == null || records.isEmpty()) {
            if (isFirstTime) {
                addView(mEmptyView)
                addView(mFooterView)
            } else {
                addView(mEmptyView, 1)
            }
        } else {
            records.forEachIndexed { index, record ->
                //设置好recordview
                val recordView = View.inflate(context, R.layout.item_record, null)
                recordView.tvTime.text = record.timeString
                recordView.tag = index
                recordView.setOnClickListener(this)
                //加入到linear中
                if (isFirstTime) {
                    addView(recordView)
                } else {
                    addView(recordView, childCount - 1)
                }

            }
            if (isFirstTime) {
                addView(mFooterView)
            }
        }

    }


    override fun onClick(v: View) {
        when (v) {
            mParentView -> {
                _onParentClick(v)
            }
            mFooterView -> {
                _onFooterClick(v)
            }
            else -> {
                _onChildClick(v.tag as Int, v)
            }
        }
    }


    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs) {
        init()
    }

    constructor(ctx: Context) : this(ctx, null) {
        init()
    }


}