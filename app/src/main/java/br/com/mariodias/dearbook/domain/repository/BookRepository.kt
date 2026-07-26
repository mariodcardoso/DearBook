package br.com.mariodias.dearbook.domain.repository

import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookResponse

interface BookRepository {

    suspend fun searchBooks(title: String): BookResponse

    suspend fun fetchBookDetails(id: String): Book

}