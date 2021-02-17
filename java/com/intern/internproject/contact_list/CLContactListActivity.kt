package com.intern.internproject.contact_list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CLLoginResponseUser
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseActivity
import com.intern.internproject.description.CLDescriptionActivity
import com.intern.internproject.followers_following.CLFollowersActivity
import kotlinx.android.synthetic.main.cl_activity_contact_list.*
import kotlinx.android.synthetic.main.cl_toolbar_contactlist.*

@Suppress("UNCHECKED_CAST")
class CLContactListActivity : CLBaseActivity() {

    var users = ArrayList<CLLoginResponseUser?>()
    var contactListViewModel: CLContactListViewModel? = null
    private var contactListAdapter: CLContactListAdapter? = null
    var visibleItemCount: Int = 0
    var pastVisibleCountItem: Int = 0
    var totalItemCount: Int = 0
    var loading: Boolean = false
    var pageId: Int = 1
    private var mOnLoadMoreListener: OnLoadMoreListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideProgressBar()
        setContentView(R.layout.cl_activity_contact_list)
        contactListViewModel = ViewModelProvider(this).get(CLContactListViewModel::class.java)
        rv_contactlist.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        contactListAdapter =
            CLContactListAdapter(this, users, object : CLContactListAdapter.ItemClickInterfaces {
                override fun itemClick(email: String?) {
                    val intent =
                        Intent(this@CLContactListActivity, CLDescriptionActivity::class.java)
                    intent.putExtra("EMAIL", email)
                    this@CLContactListActivity.startActivity(intent)
                    finish()
                }

                override fun imageClick(path1: String?) {

                }

                override fun followClick(userId: Int) {
                    showProgressBar()
                    contactListViewModel?.followRequest(userId)
                }
            })
        //contactListAdapter?.isLoading = true
        rv_contactlist.adapter = contactListAdapter
        setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                users.add(null)

                Handler().postDelayed({
                    //   remove progress item
                    //users.dropLast( 2)
                    contactListAdapter?.notifyItemRemoved(users.size - 1)
                    pageId++
                    //add items one by one
                    //When you've added the items call the setLoaded()
                    contactListViewModel?.retrieveFromServer(pageId)
                    //if you put all of the items at once call
                    // mAdapter.notifyDataSetChanged();
                }, 2000)
            }

        })
        et_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) { // filter your list from your input
                contactListViewModel?.filter(s.toString())
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        })
        btn_group_tool.setOnClickListener {
            val intent = Intent(this, CLFollowersActivity::class.java)
            startActivity(intent)
        }
        observeViewModel()
    }


    override fun onResume() {
        super.onResume()
        contactListViewModel?.retrieveFromServer(pageId)
        observeViewModel()
    }

    private fun observeViewModel() {
        contactListViewModel?.dbList?.observe(this, Observer {
            contactListAdapter?.setItem(it as ArrayList<CLLoginResponseUser?>)
            setUpAdapter()
        })
        contactListViewModel?.searchResult?.observe(this, Observer {
            contactListAdapter?.updateList(it)
        })
        contactListViewModel?.serverSuccess?.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            loading = false
            contactListAdapter?.isLoading = true
        })
        contactListViewModel?.serverFailure?.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        contactListViewModel?.followSuccess?.observe(this, Observer {
            hideProgressBar()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        contactListViewModel?.followFail?.observe(this, Observer {
            hideProgressBar()
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        contactListViewModel?.searchSuccess?.observe(this, Observer {
            contactListAdapter?.setItem(it as ArrayList<CLLoginResponseUser?>)
        })
        contactListViewModel?.searchFail?.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setUpAdapter() {
        val currentPosition =
            (rv_contactlist.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        rv_contactlist.scrollToPosition(currentPosition)
        rv_contactlist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
                totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
                pastVisibleCountItem =
                    (rv_contactlist.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if (!loading
                    && totalItemCount <= (pastVisibleCountItem + visibleItemCount)
                ) {
                    //End of the items
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener?.onLoadMore()
                    }
                    loading = true
                }
            }
        })
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    private fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }
}