package com.example.streamingaudiotest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class RadioPagerAdapter(fragmentManager: FragmentManager, val fragments: ArrayList<Radio>) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int) =
        RadioFragment.newInstance(fragments[position].URL, fragments[position].name)

    override fun getCount() = fragments.size
}