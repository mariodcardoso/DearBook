package br.com.mariodias.dearbook.domain.model


data class LibraryBook(
    val book: Book,
    val readingStatus: BookReadingStatus,
    val addedAt: Long
)

enum class BookReadingStatus{
    TO_READ,
    READING,
    READ
}