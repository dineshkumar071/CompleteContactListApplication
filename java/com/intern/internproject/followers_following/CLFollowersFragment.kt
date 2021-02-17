package com.intern.internproject.followers_following

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CLLoginResponseUser
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseFragment
import kotlinx.android.synthetic.main.cl_fragment_followers.*

@Suppress("UNCHECKED_CAST")
class CLFollowersFragment : CLBaseFragment() {
    private lateinit var followerViewModel: CLFollowersViewModel
    private var followerAdapter: CLFollowingAdapter? = null
    var users = ArrayList<CLLoginResponseUser?>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cl_fragment_followers, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        followerViewModel = ViewModelProvider(this).get(CLFollowersViewModel::class.java)
        Log.d("FRAG", "followers add")
        users.add(
            CLLoginResponseUser(
                null,
                null,
                null,
                null,
                null,
                "kumar",
                "dinesh",
                "dinu@mail.com"
            )
        )
        followerAdapter?.setItem(users)
        (activity as CLFollowersActivity).showProgressBar()
        followerViewModel.followersFromServer()
        observeViewModel()
    }

    private fun observeViewModel() {
        followerViewModel.followersResponseSuccess.observe(viewLifecycleOwner, Observer {
            (activity as CLFollowersActivity).hideProgressBar()
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })
        followerViewModel.followersResponseFail.observe(viewLifecycleOwner, Observer {
            (activity as CLFollowersActivity).hideProgressBar()
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })
        followerViewModel.followedUsers.observe(viewLifecycleOwner, Observer {
            followerAdapter?.setItem(it as ArrayList<CLLoginResponseUser?>)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_follows.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        followerAdapter =
            CLFollowingAdapter(mContext, users, object : CLFollowingAdapter.ItemClickListener {
                override fun itemClick(email: String?) {

                }

                override fun imageClick(path1: String?) {
                }

                override fun followClick(userId: Int) {
                }

            })
        rv_follows.adapter = followerAdapter
    }

}
