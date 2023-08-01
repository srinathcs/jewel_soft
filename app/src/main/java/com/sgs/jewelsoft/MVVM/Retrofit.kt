package com.sgs.jewelsoft.MVVM

import com.sgs.jewelsoft.Constant.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Retrofit {
    companion object {

        private val jewelRetrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .client(client)
                .build()
        }
        val api by lazy {
            jewelRetrofit.create(JewelSoft::class.java)
        }
    }
}