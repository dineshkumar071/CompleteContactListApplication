package com.intern.internproject.followers_following

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseFragment
import com.intern.internproject.respository.model.CLFollowRequestResponse
import kotlinx.android.synthetic.main.cl_fragment_follow_request.*

class CLFollowRequestFragment : CLBaseFragment() {
    private lateinit var followerViewModel: CLFollowersViewModel
    private var followerAdapter: CLFollowersAdapter? = null
    var users = ArrayList<CLFollowRequestResponse>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cl_fragment_follow_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_follower.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        followerAdapter =
            CLFollowersAdapter(mContext, users, object : CLFollowersAdapter.ItemClickInterface {
                override fun approve(id: Int) {
                    followerViewModel.approveRequest(id, "Accept")
                }

                override fun decline(id: Int) {
                    followerViewModel.declineRequest(id, "Decline")
                }

            })
        rv_follower.adapter = followerAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        followerViewModel = ViewModelProvider(this).get(CLFollowersViewModel::class.java)
        (activity as CLFollowersActivity).showProgressBar()
        followerViewModel.requestFromServer()
        observeViewModel()
        //users.add(CLLoginResponseUser(null,null,null,null,null,"kumar","dinesh","dinu@mail.com"))
        //followerAdapter?.setItem(users)
    }

    private fun observeViewModel() {
        followerViewModel.requesterResponseSuccess.observe(viewLifecycleOwner, Observer {
            (activity as CLFollowersActivity).hideProgressBar()
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })
        followerViewModel.requesterResponseFailure.observe(viewLifecycleOwner, Observer {
            (activity as CLFollowersActivity).hideProgressBar()
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })
        followerViewModel.requestedUsers.observe(viewLifecycleOwner, Observer {
            followerAdapter?.setItem(it as ArrayList<CLFollowRequestResponse>)
        })
        followerViewModel.decline.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })
        followerViewModel.approve.observe(viewLifecycleOwner, Observer {
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        })
    }
}