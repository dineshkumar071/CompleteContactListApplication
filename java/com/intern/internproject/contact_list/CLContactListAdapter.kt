package com.intern.internproject.contact_list

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.CLLoginResponseUser
import com.intern.internproject.R

class CLContactListAdapter(
    private var c: Context,
    var userList: ArrayList<CLLoginResponseUser?>,
    var itemClickListener: ItemClickInterfaces
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isLoading =false
    private val VIEWTYPE_FORECAST = 1
    private val VIEWTYPE_PROGRESS = 2
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactListAdapterName = itemView.findViewById(R.id.tv_name_contact_list) as TextView
        val contactListAdapterFollow = itemView.findViewById(R.id.tv_follow_contact_list) as TextView
        //val contactListAdapterImage = itemView.findViewById(R.id.iv_contact_list) as ImageView
        val contactListAdapterCard = itemView.findViewById(R.id.cv_login_user_name) as CardView
    }
    inner class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById<View>(R.id.common_progress_bar) as ProgressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*val v = LayoutInflater.from(parent.context).inflate(
            R.layout.cl_contactlistadapter,
            parent,
            false
        )
        return ViewHolder(v)*/
        return when (viewType) {
            VIEWTYPE_FORECAST -> {
                val v: View = LayoutInflater.from(parent.context).inflate(
                    R.layout.cl_contactlistadapter, parent, false
                )
                ViewHolder(v)
            }
            else -> {
                val v: View = LayoutInflater.from(parent.context).inflate(
                    R.layout.cl_progress_bar, parent, false
                )
                LoadingViewHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val user: CLLoginResponseUser? = userList[position]
            val following = user?.isFollowing
            if(following == true){
                holder.contactListAdapterFollow.text = this.c.getString(R.string.following)
            } else{
                holder.contactListAdapterFollow.text = this.c.getString(R.string.follow)
            }
            (user?.firstName + user?.lastName).also { holder.contactListAdapterName.text = it }
            holder.contactListAdapterCard.setOnClickListener {
                itemClickListener.itemClick(user?.email) }
            holder.contactListAdapterFollow.setOnClickListener {
                val userId = user?.id
                userId?.let {
                    if (holder.contactListAdapterFollow.text=="Follow") {
                        itemClickListener.followClick(it)
                    }
                } }
        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
            holder.progressBar.indeterminateDrawable?.setColorFilter(
                Color.parseColor("#D81B60"),
                PorterDuff.Mode.MULTIPLY
            )
            holder.progressBar.visibility=View.VISIBLE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == userList.size - 1 && isLoading -> VIEWTYPE_PROGRESS
            else -> VIEWTYPE_FORECAST
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    /**to update the list for database*/
    fun updateList(users: ArrayList<CLLoginResponseUser>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }
    /**set the adaptor*/
    fun setItem(valve: ArrayList<CLLoginResponseUser?>) {
        userList.clear()
        userList.addAll(valve)
        notifyDataSetChanged()
    }

    interface ItemClickInterfaces {
        fun itemClick(email:String?)
        fun imageClick(path1:String?)
        fun followClick(userId:Int)
    }
}