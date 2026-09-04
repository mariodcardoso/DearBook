package br.com.mariodias.dearbook.presentation.features.searchbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mariodias.dearbook.data.Resource
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.repository.BookRepository
import br.com.mariodias.dearbook.domain.usecase.GetLibraryBooksUseCase
import br.com.mariodias.dearbook.domain.usecase.RemoveBookFromLibraryUseCase
import br.com.mariodias.dearbook.domain.usecase.SaveBookToLibraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val repository: BookRepository,
    private val getLibraryBooksUseCase: GetLibraryBooksUseCase,
    private val saveBookToLibraryUseCase: SaveBookToLibraryUseCase,
    private val removeBookFromLibraryUseCase: RemoveBookFromLibraryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchBookUiState>(SearchBookUiState.Idle)
    val uiState: StateFlow<SearchBookUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val libraryState: StateFlow<Map<String, LibraryBook>> = getLibraryBooksUseCase().map { books ->
        books.associateBy { it.book.id }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onSearch() {
        val currentQuery = _query.value
        if (currentQuery.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchBookUiState.Loading

            when (val result = repository.searchBooks(currentQuery)) {

                is Resource.Success -> {
                    val bookList = result.response.items
                    _uiState.value = if (bookList.isEmpty()) {
                        SearchBookUiState.EmptyList
                    } else {

                        SearchBookUiState.Success(bookList)
                    }
                }

                is Resource.Error -> {
                    _uiState.value = SearchBookUiState.Error(result.exception.message.toString())
                }
            }
        }
    }


    fun onAddButtonClick(book: Book) {
        _selectedBook.value = book
    }

    fun onDismissBottomSheet() {
        _selectedBook.value = null
    }

    fun onReadingStatusClick(status: BookReadingStatus) {

        viewModelScope.launch {
            _selectedBook.value?.let {
                saveBookToLibraryUseCase(it, status)
            }
        }

    }

    fun onRemoveFromLibraryClick() {
        viewModelScope.launch {
            _selectedBook.value?.let {
                removeBookFromLibraryUseCase(it.id)
            }
        }
    }
}

sealed interface SearchBookUiState {
    object Idle : SearchBookUiState
    object Loading : SearchBookUiState
    data class Success(val bookList: List<Book>) : SearchBookUiState
    object EmptyList : SearchBookUiState
    data class Error(val errorMessage: String) : SearchBookUiState
}