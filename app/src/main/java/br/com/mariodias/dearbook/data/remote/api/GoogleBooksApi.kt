package br.com.mariodias.dearbook.data.remote.api

import br.com.mariodias.dearbook.data.remote.dto.BookDto
import br.com.mariodias.dearbook.data.remote.dto.BookResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes")
    suspend fun getBookList(
        @Query("q") query: String,
        @Query("orderBy") orderBy: String = "relevance",
        @Query("maxResults") maxResults: Int = 20
    ): BookResponseDto

    @GET("volumes/{id}")
    suspend fun getBookDetails(@Path("id") id: String): BookDto

}