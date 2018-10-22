package com.qgstudio.glass.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.lang.RuntimeException

class HomeFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentClazzList by lazy { mutableListOf<Class<*>>() }
    private val fragmentList by lazy { mutableListOf<Fragment>() }

    fun addPage(clazz: Class<*>) {
        fragmentClazzList.add(clazz)
    }

    override fun getItem(p0: Int): Fragment {
        if (fragmentList.size < p0 + 1) {
            val clazz = fragmentClazzList[p0]
            val fragment = clazz.newInstance() as? Fragment
                    ?: throw RuntimeException("${clazz.name} can't be cast to ${Fragment::class.java.name}")
            fragmentList.add(fragment)
        }
        return fragmentList[p0]
    }

    override fun getCount() = fragmentClazzList.size


}