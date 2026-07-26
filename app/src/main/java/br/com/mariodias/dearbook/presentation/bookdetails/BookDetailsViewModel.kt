package br.com.mariodias.dearbook.presentation.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val repository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailsUiState>(BookDetailsUiState.Loading)
    val uiState: StateFlow<BookDetailsUiState> = _uiState.asStateFlow()

    private val bookId: String = checkNotNull(savedStateHandle["bookId"])

    init {
        viewModelScope.launch {

            try {
                val bookDetails = repository.fetchBookDetails(bookId)
                _uiState.value = BookDetailsUiState.Success(bookDetails.volumeInfo)

            } catch (e: Exception) {

            }
        }
    }
}

sealed interface BookDetailsUiState {
    object Loading : BookDetailsUiState
    data class Success(val book: VolumeInfo) : BookDetailsUiState
}