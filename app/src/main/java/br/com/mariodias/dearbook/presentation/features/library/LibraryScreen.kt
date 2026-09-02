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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.model.VolumeInfo

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

@Preview(showBackground = true)
@Composable
fun LibraryContentSuccessPreview() {

    val books = listOf(
        LibraryBook(
            book = Book(
                id = "1",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg")
                )
            ),
            readingStatus = BookReadingStatus.TO_READ,
            addedAt = 0L
        ),
        LibraryBook(
            book = Book(
                id = "2",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg")
                )
            ),
            readingStatus = BookReadingStatus.READING,
            addedAt = 0L
        ),
        LibraryBook(
            book = Book(
                id = "3",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg")
                )
            ),
            readingStatus = BookReadingStatus.READ,
            addedAt = 0L
        ),
        LibraryBook(
            book = Book(
                id = "4",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg")
                )
            ),
            readingStatus = BookReadingStatus.READING,
            addedAt = 0L
        )
    )

    LibraryContent(Modifier, LibraryUiState.Success(books), onBookClick = {})


}