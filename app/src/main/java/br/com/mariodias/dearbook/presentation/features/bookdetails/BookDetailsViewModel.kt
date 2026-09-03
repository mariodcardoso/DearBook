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
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
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
    private val libraryRepository: LibraryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailsUiState>(BookDetailsUiState.Loading)
    val uiState: StateFlow<BookDetailsUiState> = _uiState.asStateFlow()

    private val bookId = savedStateHandle.toRoute<BookDetails>().bookId

    init {
        viewModelScope.launch {
            when (val result = bookRepository.fetchBookDetails(bookId)) {
                is Resource.Success -> {
                    _uiState.value = BookDetailsUiState.Success(result.response.volumeInfo)
                }

                is Resource.Error -> {
                    _uiState.value = BookDetailsUiState.Error(result.exception.message.toString())
                }
            }
        }
    }

    fun addToLibrary(readingStatus: BookReadingStatus) {
        val currentState = uiState.value

        if (currentState !is BookDetailsUiState.Success) return

        viewModelScope.launch {
            libraryRepository.saveBook(
                Book(bookId, currentState.book),
                readingStatus
            )
        }


    }
}

sealed interface BookDetailsUiState {
    object Loading : BookDetailsUiState
    data class Success(val book: VolumeInfo) : BookDetailsUiState
    data class Error(val errorMessage: String) : BookDetailsUiState
}