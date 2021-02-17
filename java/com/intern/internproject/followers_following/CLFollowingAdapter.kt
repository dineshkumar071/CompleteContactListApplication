package com.intern.internproject.followers_following

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.CLLoginResponseUser
import com.intern.internproject.R
import kotlinx.android.synthetic.main.cl_adapter_following.view.*

class CLFollowingAdapter(
    var context: Context,
    var userList: ArrayList<CLLoginResponseUser?>,
    var itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CLFollowingAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactListName: TextView = itemView.tv_name_following
        val contactListFollow: TextView = itemView.tv_follow_following
        val contactListImage: ImageView = itemView.iv_following
        val contactListCard: CardView = itemView.cv_following_user_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.cl_adapter_following,
            parent,
            false
        )
    )

    override fun getItemCount() = userList.size

    fun updateList(users: ArrayList<CLLoginResponseUser?>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    fun setItem(users: ArrayList<CLLoginResponseUser?>) {
        userList = users
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: CLLoginResponseUser? = userList[position]

        (user?.firstName + user?.lastName).also { holder.contactListName.text = it }


        holder.contactListCard.setOnClickListener { itemClickListener.itemClick(user?.email) }
    }

    interface ItemClickListener {
        fun itemClick(email: String?)
        fun imageClick(path1: String?)
        fun followClick(userId: Int)
    }
}