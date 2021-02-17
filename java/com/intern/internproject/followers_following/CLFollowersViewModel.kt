package com.intern.internproject.followers_following

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.intern.internproject.R
import com.intern.internproject.base.CLBaseViewModel
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.respository.model.CLFollowRequestResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CLFollowersViewModel(mApplication: Application) : CLBaseViewModel(mApplication) {
    var requesterResponseFailure = MutableLiveData<String?>()
    var requesterResponseSuccess = MutableLiveData<String?>()
    var requestedUsers = MutableLiveData<List<CLFollowRequestResponse>>()
    val approve by lazy { MutableLiveData<String?>() }
    val decline by lazy { MutableLiveData<String?>() }
    val followingResponseSuccess by lazy { MutableLiveData<String?>() }
    val followingResponseFail by lazy { MutableLiveData<String?>() }
    val followersResponseSuccess by lazy { MutableLiveData<String?>() }
    val followersResponseFail by lazy { MutableLiveData<String?>() }
    var followedUsers = MutableLiveData<List<CLFollowRequestResponse>>()
    var followingUsers = MutableLiveData<List<CLFollowRequestResponse>>()

    fun requestFromServer() {
        val token = retrieveToken()
        val call = CLRepository.getRequestUsers(token?.authToken, token?.refreshToken)
        call?.enqueue(object : Callback<List<CLFollowRequestResponse>> {
            override fun onFailure(call: Call<List<CLFollowRequestResponse>>, t: Throwable) {
                requesterResponseFailure.value = mApplication.getString(R.string.went_wrong)
            }

            override fun onResponse(
                call: Call<List<CLFollowRequestResponse>>,
                response: Response<List<CLFollowRequestResponse>>
            ) {
                if (response.isSuccessful) {
                    requesterResponseSuccess.value =
                        mApplication.getString(R.string.request_response_success)
                    requestedUsers.value = response.body()
                } else {
                    requesterResponseFailure.value =
                        mApplication.getString(R.string.request_Response_fail)
                }
            }
        })
    }

    fun followersFromServer() {
        val token = retrieveToken()
        val call = CLRepository.getFollowers(token?.authToken, token?.refreshToken)
        call?.enqueue(object : Callback<List<CLFollowRequestResponse>> {
            override fun onFailure(call: Call<List<CLFollowRequestResponse>>, t: Throwable) {
                followersResponseFail.value = mApplication.getString(R.string.went_wrong)
            }

            override fun onResponse(
                call: Call<List<CLFollowRequestResponse>>,
                response: Response<List<CLFollowRequestResponse>>
            ) {
                if (response.isSuccessful) {
                    followersResponseSuccess.value =
                        mApplication.getString(R.string.followers_request_success)
                    val res = response.body()
                    followedUsers.value = res
                    // res?.let { CLRepository.insertFollowers(it) }
                } else {
                    followersResponseFail.value =
                        mApplication.getString(R.string.followers_request_failure)
                }
            }

        })
    }

    fun followingFromServer() {
        val token = retrieveToken()
        val call = CLRepository.getFollowing(token?.authToken, token?.refreshToken)
        call?.enqueue(object : Callback<List<CLFollowRequestResponse>> {
            override fun onResponse(
                call: Call<List<CLFollowRequestResponse>>,
                response: Response<List<CLFollowRequestResponse>>
            ) {
                if (response.isSuccessful) {
                    followingResponseSuccess.value =
                        mApplication.getString(R.string.followers_request_success)
                    followingUsers.value = response.body()
                } else {
                    followingResponseFail.value =
                        mApplication.getString(R.string.followers_request_failure)
                }
            }

            override fun onFailure(call: Call<List<CLFollowRequestResponse>>, t: Throwable) {
                followingResponseFail.value =
                    mApplication.getString(R.string.followers_request_failure)
            }
        })
    }

    fun approveRequest(id: Int, status: String?) {
        val token = retrieveToken()
        val call =
            CLRepository.approveFollowRequest(id, status, token?.authToken, token?.refreshToken)
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    approve.value = mApplication.getString(R.string.approve_success)

                } else {
                    approve.value = mApplication.getString(R.string.approve_fail)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                approve.value = mApplication.getString(R.string.approve_fail)
            }
        })
    }

    fun declineRequest(id: Int, status: String?) {
        val token = retrieveToken()
        val call =
            CLRepository.declineFollowRequest(id, status, token?.authToken, token?.refreshToken)
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    decline.value = mApplication.getString(R.string.decline_success)

                } else {
                    decline.value = mApplication.getString(R.string.decline_fail)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                decline.value = mApplication.getString(R.string.decline_fail)
            }
        })
    }
}