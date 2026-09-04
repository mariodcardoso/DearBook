@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.mariodias.dearbook.presentation.features.bookdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.presentation.components.ReadingStatusBottomSheet
import br.com.mariodias.dearbook.presentation.getReadingStatusIcon
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.presentation.getStatusText
import br.com.mariodias.dearbook.presentation.getReadingStatusContentColor
import br.com.mariodias.dearbook.ui.theme.Spacing
import br.com.mariodias.dearbook.ui.theme.Sumi
import coil3.compose.AsyncImage

@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookDetailsContent(
        modifier,
        uiState,
        onRemoveBookFromLibrary = { viewModel.onRemoveBookFromLibraryClick() },
        onBackClick = { onBackClick() }

    ) { status ->
        viewModel.onReadingStatusClick(status)
    }

}

@Composable
fun BookDetailsContent(
    modifier: Modifier = Modifier,
    uiState: BookDetailsUiState,
    onRemoveBookFromLibrary: () -> Unit,
    onBackClick: () -> Unit,
    onReadingBookStatusClicked: (BookReadingStatus) -> Unit,
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var isBookInLibrary = false
    var readingStatusSelected: BookReadingStatus? = null

    Scaffold(
        modifier = modifier, topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clip(RoundedCornerShape(100))
                            .background(color = Sumi.copy(0.12f))
                            .clickable(onClick = onBackClick)
                            .padding(10.dp),
                        tint = Sumi
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Spacing.screenHorizontal)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when (uiState) {
                BookDetailsUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(64.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                is BookDetailsUiState.Success -> {

                    isBookInLibrary = uiState.isBookInLibrary
                    readingStatusSelected = uiState.readingStatus

                    AsyncImage(
                        model = uiState.book.bookCover.thumbnail,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(280.dp)
                            .width(208.dp)
                            .clip(MaterialTheme.shapes.large)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = uiState.book.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = uiState.book.authors?.firstOrNull() ?: "Author Unknown",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = stringResource(R.string.page_count, uiState.book.pageCount),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getReadingStatusColor(uiState.readingStatus),
                            contentColor = getReadingStatusContentColor(uiState.readingStatus)
                        ),
                        onClick = {
                            showBottomSheet = true
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = getReadingStatusIcon(uiState.readingStatus),
                                contentDescription = null,
                                tint = getReadingStatusContentColor(uiState.readingStatus),
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = stringResource(getStatusText(uiState.readingStatus)))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {

                        Text(
                            text = stringResource(R.string.synopsis_label),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.book.description ?: "",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                is BookDetailsUiState.Error -> {}
            }

        }

        if (showBottomSheet) {
            ReadingStatusBottomSheet(
                isBookInLibrary = isBookInLibrary,
                readingStatusSelected = readingStatusSelected,
                onReadingBookStatusClicked = onReadingBookStatusClicked,
                onRemoveBookFromLibrary = onRemoveBookFromLibrary,
                onDismissRequest = { showBottomSheet = false }
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchBookContentSuccessPreview() {

    val fakeBook = Book(
        id = "1", volumeInfo = VolumeInfo(
            title = "Norwegian Wood",
            subtitle = "",
            authors = listOf("Haruki Murakami"),
            description = "Toru Watanabe relembra sua juventude no Japão dos anos 60, marcada por amizade, perda e os primeiros grandes amores de sua vida.",
            pageCount = 296,
            bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg")
        )
    )

    BookDetailsContent(
        uiState = BookDetailsUiState.Success(
            fakeBook.volumeInfo,
            null,
            false
        ),
        onRemoveBookFromLibrary = {},
        onBackClick = {}
    ) {}
}