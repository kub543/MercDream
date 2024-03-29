package com.baszczyk.mercpiggibank3.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class MercedesApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}

private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .baseUrl(BASE_URL)
                        .build()

interface MercedesApiService {
    @GET(value = "realestate")
    fun getProperties(@Query("filter") type: String): Deferred<List<MercedesPhoto>>
}

object MercedesApi {
    val retrofitService : MercedesApiService by lazy {
        retrofit.create(MercedesApiService::class.java)
    }
}