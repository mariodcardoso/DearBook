package br.com.mariodias.dearbook.presentation.features.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.mariodias.dearbook.data.Resource
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.domain.repository.BookRepository
import br.com.mariodias.dearbook.presentation.navigation.BookDetails
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

    private val bookId = savedStateHandle.toRoute<BookDetails>().bookId

    init {
        viewModelScope.launch {

            when (val result = repository.fetchBookDetails(bookId)) {
                is Resource.Success -> {
                    _uiState.value = BookDetailsUiState.Success(result.response.volumeInfo)
                }

                is Resource.Error -> {
                    BookDetailsUiState.Error(result.exception.message.toString())
                }
            }
        }
    }
}

sealed interface BookDetailsUiState {
    object Loading : BookDetailsUiState
    data class Success(val book: VolumeInfo) : BookDetailsUiState
    data class Error(val errorMessage: String) : BookDetailsUiState
}