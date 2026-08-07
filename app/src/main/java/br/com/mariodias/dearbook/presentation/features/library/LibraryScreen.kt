package br.com.mariodias.dearbook.presentation.features.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.mariodias.dearbook.presentation.features.searchbook.ItemBookListView

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
    onBookClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LibraryContent(modifier, uiState, onBookClick)
}

@Composable
fun LibraryContent(
    modifier: Modifier = Modifier,
    uiState: LibraryUiState,
    onBookClick: (String) -> Unit
) {
    // Scaffold + TopAppBar, igual o SearchBooksScreen
    when (uiState) {
        LibraryUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center)
                )
            }
        }

        LibraryUiState.Empty -> { /* Text de "estante vazia" */
        }

        is LibraryUiState.Success -> {
            LazyColumn {
                items(uiState.books, key = { it.book.id }) { libraryBook ->
                    ItemBookListView(
                        book = libraryBook.book,
                        onClick = { onBookClick(libraryBook.book.id) }
                    )
                }
            }
        }
    }
}
