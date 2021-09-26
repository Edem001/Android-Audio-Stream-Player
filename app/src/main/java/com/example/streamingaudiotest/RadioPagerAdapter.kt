package com.example.streamingaudiotest

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class RadioPagerAdapter(fragmentManager: FragmentManager, private val radios: ArrayList<Radio>) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int) =
        RadioFragment.newInstance(radios[position].URL, radios[position].name, radios[position].ImageResID)

    override fun getCount() = radios.size
}