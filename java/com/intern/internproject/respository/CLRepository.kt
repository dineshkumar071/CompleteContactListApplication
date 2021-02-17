package com.intern.internproject.respository

import com.example.CLLoginResponseToken
import com.example.CLLoginResponseUser
import com.google.gson.GsonBuilder
import com.intern.internproject.application_database.CLFollowersDAO
import com.intern.internproject.application_database.CLUserDAO
import com.intern.internproject.application_database.CLUserDetailsDAO
import com.intern.internproject.common.CLApplication.Companion.dbInstance
import com.intern.internproject.common.CLApplication.Companion.instance
import com.intern.internproject.respository.model.*
import com.intern.internproject.retrofit.CLRetrofit
import com.intern.internproject.services.CLUserClient
import okhttp3.ResponseBody
import retrofit2.Call


object CLRepository {

    private var userDao: CLUserDAO = dbInstance.userDOA()
    var userDetail: CLUserDetailsDAO = dbInstance.userDetailsDOA()
    var followers: CLFollowersDAO = dbInstance.followersDAO()
    var BASE_URL: String = "https://jwt-api-test.herokuapp.com/"
    var userActivation: CLUserActivation? = null

    /**
     * save the authorization token and refresh token in the shared preference*/
    fun saveUserInSharedPreference(user: CLLoginResponseToken?) {
        val userGson = GsonBuilder().create()
        val listOfUsers: String? = userGson.toJson(user)
        instance.getPrefer()?.listOfUsers = listOfUsers
    }

    /**
     * retrieve the authorization token and refresh token from the shared preference*/
    fun retrieveUserFromSharedPreference(): String? = instance.getPrefer()?.listOfUsers

    /**
     * delete the refresh token and authorization token when we logout */
    fun eraseFromPreference() = instance.getPrefer()?.clearSharedPreference()


    /**
     * save the login user details*/
    fun insertUser(user: CLUSerEntity?): Call<CLUSerEntity>? {
        val client = CLRetrofit.retrofit()
        val userModel = CLUserModel()
        userModel.user = user
        userActivation?.let {
            it.Email = user?.Email
            it.passWord = user?.passWord
        }
        return client?.createAccount(userModel)
    }

    /**
     * register the user details
     * otp should be sent to the given email*/
    fun registerUser(token: String?) = CLRetrofit.retrofit()?.getActivation(token)

    /**
     * activate the user after verified*/
    fun activateUser(id: CLUserLogin?) =
        CLRetrofit.retrofit()?.loginActivation(id?.email, id?.passWord)

    /**
     * get the list of users*/
    fun getUsers(authorisation: String?, page: String?) =
        CLRetrofit.retrofit()?.getUsersAPI(authorisation, page)

    /**
     * update the user detail*/
    fun updateUser(authoriseToken: String?, refreshToken: String?, user: CLEditUser?) =
        CLRetrofit.retrofit()?.updateUser(authoriseToken, refreshToken, user)

    /**
     * logout the user*/
    fun logoutUser(authorisation: String?, refreshToken: String?) =
        CLRetrofit.retrofit()?.logOut(authorisation, refreshToken)

    /**
     * to get the refresh token and authorization token of the user when it will get fail*/
    fun toGetRefreshToken(token: String?) = CLRetrofit.retrofit()?.refreshToken(token)

    fun emailVerification(email: String?) = CLRetrofit.retrofit()?.sendEmailForGenerateOtp(email)


    /**
     * reset the user password*/
    fun resetPassword(otp: String?, password: String?) =
        CLRetrofit.retrofit()?.setPasswordSuccessfully(otp, password)

    /**
     * if the user should click the follow button ,the request will be send*/
    fun follow(userId: Int, authorisation: String?, refreshToken: String?) =
        CLRetrofit.retrofit()?.requestFollow(authorisation, refreshToken)

    fun search(text: String?, token: CLLoginResponseToken?): Call<List<CLContacListUsers1?>>? =
        CLRetrofit.retrofit()?.onlineSearch(text, token?.authToken, token?.refreshToken)

    fun getRequestUsers(
        authoriseToken: String?,
        refreshToken: String?
    ): Call<List<CLFollowRequestResponse>>? =
        CLRetrofit.retrofit()?.listOfRequests(authoriseToken, refreshToken)

    fun getFollowers(
        authorisation: String?,
        refreshToken: String?
    ): Call<List<CLFollowRequestResponse>>? =
        CLRetrofit.retrofit()?.listOfFollowers(authorisation, refreshToken)

    fun getFollowing(
        authorisation: String?,
        refreshToken: String?
    ): Call<List<CLFollowRequestResponse>>? =
        CLRetrofit.retrofit()?.listOfFollowing(authorisation, refreshToken)

    fun updateVersionName(name: String?): Call<CLAppVersioningFullResponse>? =
        CLRetrofit.retrofit()?.updateAndroid("android", name)

    fun approveFollowRequest(
        id: Int,
        status: String?,
        authorisation: String?,
        refreshToken: String?
    ): Call<ResponseBody>? =
        CLRetrofit.retrofit()?.approveRequest(id, status, authorisation, refreshToken)

    fun declineFollowRequest(
        id: Int,
        status: String?,
        authorisation: String?,
        refreshToken: String?
    ): Call<ResponseBody>? =
        CLRetrofit.retrofit()?.declineRequest(id, status, authorisation, refreshToken)

    fun insert(userRoom: CLLoginResponseUser?) = Thread {
        userRoom?.let { userDao.saveUser(it) }
    }.start()

    fun insertFollowers(userRoom: List<CLFollowers>) = Thread {
        userRoom.let { followers.saveFollowers(userRoom) }
    }.start()

    fun insertDetails(userName: String?, password: String?) {
        val user = CLUserDetails(userName, password)
        Thread {
            userDetail.insertUserDetails(user)
        }.start()
    }

    fun insertList(userRoom: List<CLLoginResponseUser>?) = Thread {
        userRoom?.let { userDao.saveUsers(it) }
    }.start()

    fun updateUserDb(user: CLLoginResponseUser?) = Thread {
        user?.let {
            userDao.updateUser(it)
        }
    }.start()

    fun deleteDataFromDatabase() = Thread {
        userDao.deleteDataBase()
        userDetail.deleteUser()
    }.start()


    fun getUserDetails(callback: callback) = Thread {
        val userDetails = userDao.readUser()
        callback.getList1(userDetails)
    }.start()


    fun getUser(callback: secondDatabaseCallBack) = Thread {
        val user = userDetail.userDetail()
        callback.getUserDetail(user)
    }.start()

    interface callback {
        fun getList1(userList: List<CLLoginResponseUser>?)
    }


    interface secondDatabaseCallBack {
        fun getUserDetail(user: CLUserDetails)
    }
}



