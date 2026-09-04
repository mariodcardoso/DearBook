package br.com.mariodias.dearbook.domain.usecase

import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import javax.inject.Inject

class SaveBookToLibraryUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    suspend operator fun invoke(book: Book, bookReadingStatus: BookReadingStatus) {
        libraryRepository.saveBook(book, bookReadingStatus)
    }
}