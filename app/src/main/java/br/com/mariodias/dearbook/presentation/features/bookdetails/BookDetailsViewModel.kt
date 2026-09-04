package br.com.mariodias.dearbook.presentation.features.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.mariodias.dearbook.data.Resource
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.domain.repository.BookRepository
import br.com.mariodias.dearbook.domain.usecase.GetLibraryBookByIdUseCase
import br.com.mariodias.dearbook.domain.usecase.RemoveBookFromLibraryUseCase
import br.com.mariodias.dearbook.domain.usecase.SaveBookToLibraryUseCase
import br.com.mariodias.dearbook.presentation.navigation.BookDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val saveBookToLibraryUseCase: SaveBookToLibraryUseCase,
    private val removeBookFromLibraryUseCase: RemoveBookFromLibraryUseCase,
    private val getLibraryBookByIdUseCase: GetLibraryBookByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailsUiState>(BookDetailsUiState.Loading)
    val uiState: StateFlow<BookDetailsUiState> = _uiState.asStateFlow()

    private val bookId = savedStateHandle.toRoute<BookDetails>().bookId

    init {
        getBookDetails()
    }

    fun getBookDetails() {
        viewModelScope.launch {
            when (val result = bookRepository.fetchBookDetails(bookId)) {
                is Resource.Success -> {
                    val libraryStatus = getLibraryBookByIdUseCase(bookId)

                    _uiState.value = BookDetailsUiState.Success(
                        result.response.volumeInfo,
                        libraryStatus?.readingStatus,
                        libraryStatus?.isInLibrary ?: false

                    )
                }

                is Resource.Error -> {
                    _uiState.value = BookDetailsUiState.Error(result.exception.message.toString())
                }
            }
        }

    }

    fun onReadingStatusClick(readingStatus: BookReadingStatus) {
        val currentState = uiState.value
        if (currentState !is BookDetailsUiState.Success) return

        viewModelScope.launch {
            saveBookToLibraryUseCase(
                Book(bookId, currentState.book),
                readingStatus
            )
            _uiState.value = BookDetailsUiState.Success(currentState.book, readingStatus, true)
        }
    }

    fun onRemoveBookFromLibraryClick(){
        val currentState = uiState.value
        if (currentState !is BookDetailsUiState.Success) return

        viewModelScope.launch {
            removeBookFromLibraryUseCase(bookId)

            _uiState.value = BookDetailsUiState.Success(currentState.book, null, false)
        }
    }
}

sealed interface BookDetailsUiState {
    object Loading : BookDetailsUiState
    data class Success(
        val book: VolumeInfo,
        val readingStatus: BookReadingStatus?,
        val isBookInLibrary: Boolean
    ) :
        BookDetailsUiState
    data class Error(val errorMessage: String) : BookDetailsUiState
}