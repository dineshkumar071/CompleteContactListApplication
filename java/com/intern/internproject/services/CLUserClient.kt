package com.intern.internproject.services


import com.example.CLLoginResponse
import com.intern.internproject.respository.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CLUserClient {

    @POST("users")
    fun createAccount(@Body user: CLUserModel?): Call<CLUSerEntity>

    @GET("users/confirmation")
    fun getActivation(@Query("confirmation_token") token: String?): Call<CLUSerEntity>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("users/sign_in?")
    fun loginActivation(
        @Field("user[login]") email: String?,
        @Field("user[password]") password: String?
    ): Call<CLLoginResponse>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("home?")
    fun getUsersAPI(
        @Header("Authorization") author: String?,
        @Query("page") token: String?
    ): Call<CLContacListUsers1>

    //@Header("Content-Type: application/json")
    @PUT("users?")
    fun updateUser(
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?,
        @Body user: CLEditUser?
    ): Call<ResponseBody>

    @DELETE("users/sign_out")
    fun logOut(
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<ResponseBody>

    @GET("get_auth_token")
    fun refreshToken(@Header("Refresh") refresh: String?): Call<CLRefreshTokenResponse>

    @POST("users/password")
    @FormUrlEncoded
    fun sendEmailForGenerateOtp(@Field("user[email]") email: String?): Call<ResponseBody>

    @PUT("users/password")
    @FormUrlEncoded
    fun setPasswordSuccessfully(
        @Field("user[reset_password_token]") otp: String?,
        @Field("user[password]") password: String?
    ): Call<ResponseBody>

    @GET("requests?search=sidd&view_info=requests")
    fun onlineSearch(
        @Query("search") searchText: String?,
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refreshToken: String?
    ): Call<List<CLContacListUsers1?>>

    @POST("relationships?user_id=1")
    fun requestFollow(
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<ResponseBody>

    @GET("requests?view_info=requests")
    fun listOfRequests(
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<List<CLFollowRequestResponse>>

    @GET("requests?view_info=Followers")
    fun listOfFollowers(
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<List<CLFollowRequestResponse>>

    @GET("requests?view_info=Followings")
    fun listOfFollowing(
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<List<CLFollowRequestResponse>>

    @PUT("relationships?")
    fun approveRequest(
        @Query("following_id") id: Int,
        @Query("status") status: String?,
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<ResponseBody>

    @PUT("relationships?")
    fun declineRequest(
        @Query("following_id") id: Int,
        @Query("status") status: String?,
        @Header("Authorization") authorToken: String?,
        @Header("Refresh") refToken: String?
    ): Call<ResponseBody>

    @GET("check_app_version?")
    fun updateAndroid(
        @Query("os_type") type: String?,
        @Query("app_version") versionName: String?
    ): Call<CLAppVersioningFullResponse>

}