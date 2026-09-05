package br.com.mariodias.dearbook.presentation.features.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import br.com.mariodias.dearbook.ui.theme.Matcha
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Uguisu

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
    onBookClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LibraryContent(modifier, uiState, onBookClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryContent(
    modifier: Modifier = Modifier,
    uiState: LibraryUiState,
    onBookClick: (String) -> Unit
) {

    Scaffold(
        modifier = modifier
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
        ) {

            Text(
                text = "My Library",
                style = MaterialTheme.typography.headlineMedium,
                color = Sumi
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "12 books collected",
                    style = MaterialTheme.typography.labelMedium,
                    color = Uguisu
                )

                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    tint = Uguisu,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .size(5.dp)
                )

                Text(
                    text = "2 books reading",
                    style = MaterialTheme.typography.labelMedium,
                    color = Matcha,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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

                LibraryUiState.Empty -> { /* Text de "estante vazia" */ }

                is LibraryUiState.Success -> {

                    Text(
                        text = "All books",
                        style = MaterialTheme.typography.titleLarge,
                        color = Sumi
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 96.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {

                            items(uiState.books, key = { it.book.id }) { libraryBook ->
                                ItemLibraryBookView(
                                    libraryBook,
                                    onClick = { onBookClick(libraryBook.book.id) })
                            }

                        }
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
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg"),
                    averageRating = 3.5f
                )
            ),
            readingStatus = BookReadingStatus.TO_READ,
            isInLibrary = true,
            addedAt = 0L
        ),
        LibraryBook(
            book = Book(
                id = "2",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg"),
                    averageRating = 3.5f
                )
            ),
            readingStatus = BookReadingStatus.READING,
            isInLibrary = true,
            addedAt = 0L
        ),
        LibraryBook(
            book = Book(
                id = "3",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg"),
                    averageRating = 3.5f
                )
            ),
            readingStatus = BookReadingStatus.READ,
            isInLibrary = true,
            addedAt = 0L
        ),
        LibraryBook(
            book = Book(
                id = "4",
                volumeInfo = VolumeInfo(
                    title = "Norwegian Wood",
                    subtitle = "",
                    pageCount = 296,
                    bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg"),
                    averageRating = 3.5f
                )
            ),
            readingStatus = BookReadingStatus.READING,
            isInLibrary = true,
            addedAt = 0L
        )
    )

    LibraryContent(Modifier, LibraryUiState.Success(books), onBookClick = {})


}