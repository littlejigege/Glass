package com.qgstudio.glass.spothistory

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.qgstudio.glass.App
import com.qgstudio.glass.R
import com.qgstudio.glass.common.model.data.Record
import com.qgstudio.glass.common.model.db.AppDatabase
import com.qgstudio.glass.common.model.db.AppDatabase_Impl
import com.qgstudio.glass.showToast
import kotlinx.android.synthetic.main.fragment_spot.*
import kotlinx.android.synthetic.main.fragment_spot.view.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

class SpotHistoryFragment : Fragment() {
    private var records = listOf<Record>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_spot, container, false)
        rootView.twoLevelItem.onParentClick { showToast("Parent Clicked") }
        rootView.twoLevelItem.onChildClick { pos, _ -> showToast("$pos ${records[pos].timeString}") }
        rootView.twoLevelItem.onFooterClick { showToast("Footer Clicked") }
        GlobalScope.launch(Dispatchers.Main) {
            val job = async(Dispatchers.IO) { getRecords("2018-02-02") }
            rootView.twoLevelItem.addChilds(job.await().also { records = it })
        }

        return rootView
    }

    suspend fun getRecords(date: String): List<Record> {
        return AppDatabase
                .getInstance(App.instance)
                .recordDao()
                .getNormalsOrderByTime()
    }
}