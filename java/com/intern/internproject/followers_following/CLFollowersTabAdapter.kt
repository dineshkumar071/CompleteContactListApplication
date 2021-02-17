package com.intern.internproject.followers_following

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CLFollowersTabAdapter(fm :FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                Log.d("FRAG","first")
                CLFollowRequestFragment()
            }
            1->{
                Log.d("FRAG","second")
                CLFollowersFragment()
            }
            else->{
                Log.d("FRAG","third")
                return CLFollowingFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0->"Requests"
            1->"Followers"
            else->"Following"
        }
    }
}