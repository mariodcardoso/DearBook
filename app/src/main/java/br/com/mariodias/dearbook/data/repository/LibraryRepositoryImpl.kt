package br.com.mariodias.dearbook.data.repository

import br.com.mariodias.dearbook.data.local.dao.LibraryDao
import br.com.mariodias.dearbook.data.local.entity.LibraryEntity
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    val libraryDao: LibraryDao
) : LibraryRepository {

    override suspend fun saveBook(
        book: Book,
        status: BookReadingStatus
    ) {
        libraryDao.upsert(
            book.toEntity(status = status, true, addedAt = System.currentTimeMillis())
        )
    }

    override suspend fun removeBook(libraryBook: LibraryBook) {
        libraryDao.remove(
            libraryBook.book.toEntity(libraryBook.readingStatus, false, libraryBook.addedAt)
        )
    }

    override fun getAll(): Flow<List<LibraryBook>> {
        return libraryDao.getAll().map { entities -> entities.map { entity -> entity.toDomain() } }
    }

    override suspend fun getById(id: String): LibraryBook? {
        return libraryDao.getById(id)?.toDomain()
    }

}

fun Book.toEntity(
    status: BookReadingStatus,
    isInLibrary: Boolean,
    addedAt: Long,
    synced: Boolean = false
): LibraryEntity {
    return LibraryEntity(
        id = id,
        title = volumeInfo.title,
        author = volumeInfo.authors?.joinToString(", ") ?: "",
        coverUrl = volumeInfo.bookCover.thumbnail,
        status = status,
        isInLibrary = isInLibrary,
        addedAt = addedAt,
        synced = synced
    )
}

fun LibraryEntity.toDomain(): LibraryBook {
    return LibraryBook(
        book = Book(
            id = id,
            volumeInfo = VolumeInfo(
                title = title,
                subtitle = "",
                authors = listOf(author),
                description = "",
                pageCount = 0,
                bookCover = BookCover(thumbnail = coverUrl),
                averageRating = 0f
            )
        ),
        readingStatus = status, isInLibrary = isInLibrary,
        addedAt = addedAt
    )
}