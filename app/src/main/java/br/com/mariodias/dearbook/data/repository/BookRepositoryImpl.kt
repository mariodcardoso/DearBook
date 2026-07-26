package br.com.mariodias.dearbook.data.repository

import br.com.mariodias.dearbook.data.remote.api.GoogleBooksApi
import br.com.mariodias.dearbook.data.remote.dto.BookCoverDto
import br.com.mariodias.dearbook.data.remote.dto.BookDto
import br.com.mariodias.dearbook.data.remote.dto.BookResponseDto
import br.com.mariodias.dearbook.data.remote.dto.VolumeInfoDto
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookResponse
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: GoogleBooksApi
) : BookRepository {

    override suspend fun searchBooks(title: String): BookResponse {
        return api.getBookList(title).toDomain()
    }
}

fun BookResponseDto.toDomain(): BookResponse {
    return BookResponse(
        totalItems = totalItems,
        items = items.map { it.toDomain() }
    )
}

fun BookDto.toDomain(): Book {
    return Book(
        id = id,
        volumeInfo = volumeInfo.toDomain()
    )
}

fun VolumeInfoDto.toDomain(): VolumeInfo {
    return VolumeInfo(
        title = title ?: "",
        subtitle = subtitle ?: "",
        authors = authors ?: emptyList(),
        description = description,
        pageCount = pageCount ?: 0,
        bookCover = bookCover.toDomain()
    )
}

fun BookCoverDto.toDomain(): BookCover {
    return BookCover(
        thumbnail = thumbnail ?: ""
    )
}

