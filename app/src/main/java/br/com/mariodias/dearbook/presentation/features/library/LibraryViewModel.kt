package br.com.mariodias.dearbook.presentation.features.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    libraryRepository: LibraryRepository
) : ViewModel() {

    val uiState: StateFlow<LibraryUiState> = libraryRepository.getAll().map { books ->
        if (books.isEmpty()) LibraryUiState.Empty else LibraryUiState.Success(books)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LibraryUiState.Loading
    )

}

sealed interface LibraryUiState {
    object Loading : LibraryUiState
    object Empty : LibraryUiState
    data class Success(val books: List<LibraryBook>) : LibraryUiState
}
