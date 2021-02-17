package com.intern.internproject.base

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.CLLoginResponseToken
import com.example.CLLoginResponseUser
import com.google.gson.Gson
import com.intern.internproject.respository.CLRepository
import org.greenrobot.eventbus.EventBus

open class CLBaseViewModel (val mApplication: Application) : AndroidViewModel(mApplication){
    var firstName: String? = null
    var lastName: String? = null
    var companyName: String? = null
    var eMail: String? = null
    var phoneNumber: String? = null
    var passWord: String? = null
    var street1: String? = null
    var street2: String? = null
    var city: String? = null
    var state: String? = null
    var postCode: String? = null
    var path: String? = null
    var users: List<CLLoginResponseUser>? = null
    val dbList by lazy{ MutableLiveData<List<CLLoginResponseUser>>() }
    val myEvent: EventBus = EventBus.getDefault()
    fun retrieveFromDatabase() {

        CLRepository.getUserDetails(object : CLRepository.callback {
            override fun getList1(userList: List<CLLoginResponseUser>?) {

                users = userList
                Handler(Looper.getMainLooper()).post {
                    // things to do on the main thread
                    dbList.value = users

                }
            }
        })
    }
     fun retrieveToken(): CLLoginResponseToken? {
        val gson = Gson()
        return gson.fromJson(
            CLRepository.retrieveUserFromSharedPreference(),
            CLLoginResponseToken::class.java
        )
    }

}