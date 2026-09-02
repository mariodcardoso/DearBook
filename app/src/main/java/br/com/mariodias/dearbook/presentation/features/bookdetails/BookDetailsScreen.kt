@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.mariodias.dearbook.presentation.features.bookdetails

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.ui.theme.Spacing
import coil3.compose.AsyncImage

@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookDetailsContent(
        modifier,
        uiState,
        onAddToLibraryClick = { viewModel.addToLibrary() }
    )

}

@Composable
fun BookDetailsContent(
    modifier: Modifier = Modifier,
    uiState: BookDetailsUiState,
    onAddToLibraryClick: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier, topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.book_details_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(Spacing.screenHorizontal)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            when (uiState) {
                BookDetailsUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(64.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                is BookDetailsUiState.Success -> {
                    AsyncImage(
                        model = uiState.book.bookCover.thumbnail,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(280.dp)
                            .width(192.dp)
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
                        onClick = onAddToLibraryClick
                    ) {
                        Text(text = stringResource(R.string.add_to_library_button))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {

                        Text(
                            text = stringResource(R.string.synopsis_label),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
        uiState = BookDetailsUiState.Success(fakeBook.volumeInfo),
        onAddToLibraryClick = {}
    )
}

