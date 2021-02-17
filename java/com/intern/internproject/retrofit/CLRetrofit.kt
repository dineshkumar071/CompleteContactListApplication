package com.intern.internproject.retrofit

import com.example.CLLoginResponseToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intern.internproject.respository.CLRepository
import com.intern.internproject.services.CLNetworkInterceptor
import com.intern.internproject.services.CLUserClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object CLRetrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttp = OkHttpClient.Builder().addInterceptor(logger).addInterceptor(HttpInterceptor()).addInterceptor(CLNetworkInterceptor())
    fun retrofit(): CLUserClient? {

        return Retrofit.Builder()
            .baseUrl(CLRepository.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttp.build())
            .build().create(CLUserClient::class.java)
    }
}

class HttpInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //MAKE SYNCHRONIZED
        synchronized(this) {
            val originalRequest = chain.request()
            val authenticationRequest = originalRequest.newBuilder()
                .addHeader("Accept","application/json").build()
            val initialResponse = chain.proceed(authenticationRequest)


            when {
                ( initialResponse.code ==500 || initialResponse.code == 440 ) -> {
                    //RUN BLOCKING!!
                    val responseNewTokenLoginModel = CLRepository.toGetRefreshToken(Gson().fromJson(CLRepository.retrieveUserFromSharedPreference(),
                        CLLoginResponseToken::class.java).refreshToken)?.execute()

                    return when {
                        responseNewTokenLoginModel == null || responseNewTokenLoginModel.code() != 200 -> {
                            CLRepository.deleteDataFromDatabase()
                            CLRepository.eraseFromPreference()
                            return@synchronized null
                        }
                        else -> {
                            responseNewTokenLoginModel.body()?.authToken.let {
                                val token= Gson().fromJson(CLRepository.retrieveUserFromSharedPreference(),
                                    CLLoginResponseToken::class.java)
                                val refToken=token.refreshToken
                                val newToken=CLLoginResponseToken()
                                newToken.refreshToken=refToken
                                newToken.authToken=it
                                CLRepository.saveUserInSharedPreference(newToken)
                            }
                            val newAuthenticationRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer " + responseNewTokenLoginModel.body()?.authToken).build()
                            chain.proceed(newAuthenticationRequest)
                        }
                    }
                }
                else -> return initialResponse
            }
        }
    }
}