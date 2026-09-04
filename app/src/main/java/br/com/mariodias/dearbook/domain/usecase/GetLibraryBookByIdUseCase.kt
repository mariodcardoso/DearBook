package br.com.mariodias.dearbook.domain.usecase

import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import javax.inject.Inject

class GetLibraryBookByIdUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    operator suspend fun invoke(bookId: String): LibraryBook? {
        return libraryRepository.getById(bookId)
    }

}