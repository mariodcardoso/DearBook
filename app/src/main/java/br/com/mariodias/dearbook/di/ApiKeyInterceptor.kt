package br.com.mariodias.dearbook.di

import br.com.mariodias.dearbook.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlComKey = chain.request().url.newBuilder()
            .addQueryParameter("key", BuildConfig.GOOGLE_BOOKS_API_KEY)
            .build()

        val requestComKey = chain.request().newBuilder()
            .url(urlComKey)
            .build()

        return chain.proceed(requestComKey)
    }
}