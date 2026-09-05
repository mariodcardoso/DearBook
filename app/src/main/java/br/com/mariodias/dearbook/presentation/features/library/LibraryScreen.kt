package br.com.mariodias.dearbook.presentation.features.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.presentation.getReadingStatusIcon
import br.com.mariodias.dearbook.presentation.getStatusText
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Matcha
import br.com.mariodias.dearbook.ui.theme.Spacing
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Uguisu

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
    onBookClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedStatus by viewModel.selectedStatus.collectAsStateWithLifecycle()

    LibraryContent(
        modifier,
        uiState,
        selectedStatus,
        viewModel::onFilterClick,
        onBookClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryContent(
    modifier: Modifier = Modifier,
    uiState: LibraryUiState,
    selectedStatus: BookReadingStatus?,
    onFilterClick: (BookReadingStatus?) -> Unit,
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
                text = stringResource(R.string.library_title_my_library),
                style = MaterialTheme.typography.headlineMedium,
                color = Sumi
            )

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val totalBooks = uiState.quantityByStatus.values.sum()

                        Text(
                            text = "$totalBooks books collected",
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
                            text = "${uiState.quantityByStatus[BookReadingStatus.READING]} books reading",
                            style = MaterialTheme.typography.labelMedium,
                            color = Matcha,
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(Modifier.horizontalScroll(rememberScrollState())) {
                        BookReadingStatus.entries.forEach { status ->
                            val statusColor = getReadingStatusColor(status)
                            val isSelected = status == selectedStatus

                            FilterChip(
                                onClick = {
                                    onFilterClick(status)
                                },
                                label = {
                                    Text(
                                        text = stringResource(getStatusText(status)),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = uiState.quantityByStatus[status].toString(),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Sumi.copy(alpha = 0.6f)
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = getReadingStatusIcon(status),
                                        contentDescription = null,
                                        tint = statusColor
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = statusColor.copy(alpha = 0.2f),
                                    selectedLabelColor = Sumi
                                ),
                                selected = isSelected,
                                modifier = Modifier.padding(horizontal = Spacing.xs),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (isSelected) statusColor else Kinari
                                ),
                                shape = RoundedCornerShape(50)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val getStatusText = when (selectedStatus) {
                        BookReadingStatus.TO_READ -> "Want to read"
                        BookReadingStatus.READING -> "Reading"
                        BookReadingStatus.READ -> "Books completed"
                        null -> "All Books"
                    }

                    Text(
                        text = getStatusText + " (${uiState.books.size})",
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

    val quantityByStatus = mapOf<BookReadingStatus, Int>(
        Pair(BookReadingStatus.TO_READ, 2),
        Pair(BookReadingStatus.READING, 3),
        Pair(BookReadingStatus.READ, 4),
    )

    LibraryContent(
        Modifier,
        LibraryUiState.Success(books, quantityByStatus),
        null,
        onFilterClick = {},
        onBookClick = {})


}