package com.intern.internproject.followers_following

import android.os.Bundle
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseActivity
import kotlinx.android.synthetic.main.cl_activity_follwers.*


class CLFollowersActivity : CLBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_activity_follwers)

        val fragmentAdapter = CLFollowersTabAdapter(supportFragmentManager)
        ViewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(ViewPager)
        /*tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tabLayout.selectedTabPosition) {
                    0 -> {
                        CLFollowRequestFragment()
                    }
                    1 -> {
                        CLFollowersFragment()
                    }
                    else -> {
                        CLFollowingFragment()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })*/
    }
}
