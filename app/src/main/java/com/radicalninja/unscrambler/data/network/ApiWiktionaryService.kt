package com.radicalninja.unscrambler.data.network

import com.radicalninja.unscrambler.data.network.response.SearchQueryResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// url example: https://en.wiktionary.org/w/api.php?action=query&format=json&titles=word|notaword

interface ApiWiktionaryService {

    @GET("api.php")
    suspend fun getWords(
        @Query("titles") titles: String,
        @Query("action") action: String = DEFAULT_ACTION
    ) : Response<SearchQueryResponse>

    companion object {
        private const val BASE_URL = "https://en.wiktionary.org/w/"
        private const val DEFAULT_ACTION = "query"
        private const val TYPE_FORMAT = "format"
        private const val VALUE_FORMAT = "json"

        operator fun invoke(): ApiWiktionaryService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(TYPE_FORMAT, VALUE_FORMAT)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWiktionaryService::class.java)
        }
    }

}