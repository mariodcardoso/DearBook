package br.com.mariodias.dearbook.presentation.searchbook

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

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onSearch() {
        val currentQuery = _query.value
        if (currentQuery.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchBookUiState.Loading

            try {
                val bookList = repository.searchBooks(currentQuery).items

                _uiState.value = if (bookList.isEmpty()) {
                    SearchBookUiState.EmptyList
                } else {
                    SearchBookUiState.Success(bookList)
                }
            } catch (e: Exception) {
                _uiState.value = SearchBookUiState.EmptyList
            }
        }
    }
}


sealed interface SearchBookUiState {
    object Idle : SearchBookUiState
    object Loading : SearchBookUiState
    data class Success(val bookList: List<Book>) : SearchBookUiState
    object EmptyList : SearchBookUiState
}