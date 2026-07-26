package br.com.mariodias.dearbook.data.remote.api

import br.com.mariodias.dearbook.BuildConfig
import br.com.mariodias.dearbook.data.remote.dto.BookResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes")
    suspend fun getBookList(
        @Query("q") query: String,
        @Query("key") apiKey: String = BuildConfig.GOOGLE_BOOKS_API_KEY
    ): BookResponseDto

}