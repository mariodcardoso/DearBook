package br.com.mariodias.dearbook.data.repository

import androidx.core.text.HtmlCompat
import br.com.mariodias.dearbook.data.Resource
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

    override suspend fun searchBooks(title: String): Resource<BookResponse> {
        return try {
            Resource.Success(api.getBookList(title).toDomain())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun fetchBookDetails(id: String): Resource<Book> {
        return try {
            Resource.Success(api.getBookDetails(id).toDomain())
        } catch (e: Exception) {
            Resource.Error(e)
        }
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
        description = HtmlCompat.fromHtml(
            description ?: "",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString(),
        pageCount = pageCount ?: 0,
        bookCover = bookCover?.toDomain() ?: BookCover("")
    )
}

fun BookCoverDto.toDomain(): BookCover {
    return BookCover(
        thumbnail = (thumbnail ?: "").replace("http://", "https://")
    )
}

