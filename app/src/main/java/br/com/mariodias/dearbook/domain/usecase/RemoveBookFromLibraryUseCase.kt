package br.com.mariodias.dearbook.domain.usecase

import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import javax.inject.Inject

class RemoveBookFromLibraryUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    suspend operator fun invoke(bookId: String) {
        val libraryBook = libraryRepository.getById(bookId)

        libraryBook?.let {
            libraryRepository.removeBook(libraryBook)
        }

    }
}