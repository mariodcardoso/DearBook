package br.com.mariodias.dearbook.domain.repository

import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    suspend fun saveBook(book: Book, status: BookReadingStatus)
    suspend fun removeBook(libraryBook: LibraryBook)
    fun getAll(): Flow<List<LibraryBook>>
    suspend fun getById(id: String): LibraryBook?
}