package br.com.mariodias.dearbook.presentation.features.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
            Box(modifier = Modifier.padding(18.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 96.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(uiState.books, key = { it.book.id }) { libraryBook ->
                        ItemLibraryBookView(libraryBook)
                    }

                }
            }
        }
    }
}