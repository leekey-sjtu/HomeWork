package com.bytedance.homework.homework3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

//class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private var fragments: List<Fragment>) :
class ViewPager2Adapter(fa: FragmentActivity, private var fragments: List<Fragment>) :
    FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}