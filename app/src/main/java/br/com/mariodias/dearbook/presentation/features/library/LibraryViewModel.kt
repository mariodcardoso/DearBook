package br.com.mariodias.dearbook.presentation.features.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.usecase.GetLibraryBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getLibraryBooksUseCase: GetLibraryBooksUseCase
) : ViewModel() {

    private val _selectedStatus = MutableStateFlow<BookReadingStatus?>(null)
    val selectedStatus = _selectedStatus.asStateFlow()

    val uiState: StateFlow<LibraryUiState> = combine(
        getLibraryBooksUseCase(),
        _selectedStatus
    ) { books, selectedStatus ->

        val filteredBooks = if (selectedStatus == null) {
            books
        } else {
            books.filter { book -> book.readingStatus == selectedStatus }
        }

        if (books.isEmpty()) LibraryUiState.Empty else LibraryUiState.Success(filteredBooks)

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LibraryUiState.Loading
    )


    fun onFilterClick(selectedStatus: BookReadingStatus) {
        _selectedStatus.value = if (_selectedStatus.value == selectedStatus) null else selectedStatus
    }
}

sealed interface LibraryUiState {
    object Loading : LibraryUiState
    object Empty : LibraryUiState
    data class Success(val books: List<LibraryBook>) : LibraryUiState
}
