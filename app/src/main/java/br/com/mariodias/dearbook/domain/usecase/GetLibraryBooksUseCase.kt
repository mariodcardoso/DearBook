package br.com.mariodias.dearbook.domain.usecase

import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLibraryBooksUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    operator fun invoke(): Flow<List<LibraryBook>> {
        return libraryRepository.getAll()
    }
}