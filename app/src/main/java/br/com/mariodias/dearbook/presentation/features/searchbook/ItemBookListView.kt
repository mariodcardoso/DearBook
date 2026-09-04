package br.com.mariodias.dearbook.presentation.features.searchbook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import br.com.mariodias.dearbook.presentation.getReadingStatusIcon
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.presentation.getStatusText
import br.com.mariodias.dearbook.presentation.getReadingStatusContentColor
import br.com.mariodias.dearbook.ui.theme.Spacing
import coil3.compose.AsyncImage

@Composable
fun ItemBookListView(
    book: Book,
    readingStatus: BookReadingStatus?,
    onClick: () -> Unit,
    onAddButtonClicked: (book: Book) -> Unit,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            AsyncImage(
                model = book.volumeInfo.bookCover.thumbnail,
                contentDescription = stringResource(R.string.book_cover_description),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(96.dp)
                    .width(66.dp)
                    .clip(MaterialTheme.shapes.small)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = Spacing.sm)
                .weight(1f)
        ) {
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface

            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            Text(
                text = book.volumeInfo.authors?.firstOrNull() ?: "Autor desconhecido",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FilledIconButton(
            modifier = Modifier.size(28.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = getReadingStatusColor(readingStatus),
                contentColor = getReadingStatusContentColor(readingStatus)
            ),
            onClick = { onAddButtonClicked(book) }

        ) {
            Icon(
                imageVector = getReadingStatusIcon(readingStatus),
                contentDescription = stringResource(getStatusText(readingStatus)),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemBookListViewPreview() {
    ItemBookListView(
        book = Book(
            id = "1",
            volumeInfo = VolumeInfo(
                title = "Norwegian Wood",
                subtitle = "",
                authors = listOf("Haruki Murakami"),
                description = "",
                pageCount = 296,
                bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg")
            )
        ),
        readingStatus = BookReadingStatus.READ,
        onClick = {},
        onAddButtonClicked = {}
    )
}