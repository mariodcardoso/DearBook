@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.mariodias.dearbook.presentation.searchbook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.ui.theme.Spacing

private val fakeBooks = listOf(
    Book(
        id = "1",
        volumeInfo = VolumeInfo(
            title = "Norwegian Wood",
            subtitle = "",
            authors = listOf("Haruki Murakami"),
            description = "Toru Watanabe relembra sua juventude no Japão dos anos 60.",
            pageCount = 296,
            bookCover = BookCover(thumbnail = "")
        )
    ),
    Book(
        id = "2",
        volumeInfo = VolumeInfo(
            title = "Convenience Store Woman",
            subtitle = "",
            authors = listOf("Sayaka Murata"),
            description = "Keiko Furukura trabalha há 18 anos na mesma loja de conveniência.",
            pageCount = 176,
            bookCover = BookCover(thumbnail = "")
        )
    ),
    Book(
        id = "3",
        volumeInfo = VolumeInfo(
            title = "O livro do chá",
            subtitle = "",
            authors = listOf("Okakura Kakuzo"),
            description = "Um clássico sobre a cerimônia do chá e a estética japonesa.",
            pageCount = 104,
            bookCover = BookCover(thumbnail = "")
        )
    )
)
@Composable
fun SearchBooksScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchBookViewModel = hiltViewModel(),
    onBookClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()

    SearchBookContent(
        modifier = modifier,
        uiState = uiState,
        query = query,
        onQueryChange = viewModel::onQueryChange,
        onSearch = viewModel::onSearch,
        onBookClick = onBookClick
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBookContentSuccessPreview() {
    SearchBookContent(
        uiState = SearchBookUiState.Success(fakeBooks),
        onBookClick = {}


    )
}

@Composable
fun SearchBookContent(
    modifier: Modifier = Modifier,
    uiState: SearchBookUiState = SearchBookUiState.Idle,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onBookClick: (String) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_screen_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = Spacing.screenHorizontal)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch()
                    keyboardController?.hide()
                })
            )

            when (uiState) {
                SearchBookUiState.Idle -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.search_idle_hint),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                SearchBookUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(64.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                is SearchBookUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.padding(top = Spacing.cardGap),
                        verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
                    ) {
                        items(uiState.bookList, key = { it.id }) { book ->
                            ItemBookListView(book, onClick = { onBookClick(book.id)})
                        }
                    }
                }

                SearchBookUiState.EmptyList -> {
                    Text(text = stringResource(R.string.search_empty_list))
                }

                is SearchBookUiState.Error -> {}
            }

        }
    }
}