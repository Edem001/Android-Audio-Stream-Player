package com.example.streamingaudiotest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RadioPagerAdapter(fragmentManager: FragmentManager, private val radios: ArrayList<Radio>) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int):Fragment {
        return RadioFragment.newInstance(
            radios[position].URL,
            radios[position].name,
            radios[position].ImageResID
        )
    }

    override fun getCount() = radios.size
}