package br.com.mariodias.dearbook.presentation.searchbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchBookUiState>(SearchBookUiState.Idle)
    val uiState: StateFlow<SearchBookUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = SearchBookUiState.Loading

            try {
                val bookList = repository.searchBooks("Ikigai").items

                _uiState.value = SearchBookUiState.Success(bookList)
            } catch (e: Exception) {

            }


        }
    }


}


sealed interface SearchBookUiState {
    object Idle : SearchBookUiState
    object Loading : SearchBookUiState
    data class Success(val bookList: List<Book>) : SearchBookUiState
}