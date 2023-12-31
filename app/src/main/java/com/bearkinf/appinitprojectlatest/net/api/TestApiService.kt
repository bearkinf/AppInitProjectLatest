package com.bearkinf.appinitprojectlatest.net.api

import android.util.Log
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


interface TestApiService {

    // 인터페이스 안에서 static 변수처럼 사용.
    // 동반 객체.
    companion object {

        private val BASE_URL: String = "https://api.github.com/"

        private val httpClient: OkHttpClient = OkHttpClient.Builder()
            // Timeout check
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        private val retrofit: Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

//        private fun httpClient(): OkHttpClient {
//            Log.e("bear", "fun httpClient")
//            return OkHttpClient.Builder()
//                // Timeout check
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .build()
////
//        }
//
//        private fun rettt(): Retrofit {
//            Log.e("bear", "fun rettt")
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(httpClient())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }


        val service: TestApiService = retrofit.create(TestApiService::class.java)

        // 특정 인터페이스가 아닌 기능으로 구현.
//        fun <T> service2(t: Class<T>): T {
//            return retrofit.create(t)
//        }
//
//        fun <T> service33(t: Class<T>) = retrofit.create(t)
    }

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Single<String>
}