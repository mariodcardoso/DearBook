package br.com.mariodias.dearbook.presentation.features.searchbook

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.presentation.getReadingStatusContentColor
import br.com.mariodias.dearbook.presentation.getReadingStatusIcon
import br.com.mariodias.dearbook.presentation.getStatusText
import br.com.mariodias.dearbook.ui.theme.Amber
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Uguisu
import br.com.mariodias.dearbook.ui.theme.Washi
import coil3.compose.AsyncImage

@Composable
fun ItemBookListView(
    book: Book,
    readingStatus: BookReadingStatus?,
    onClick: () -> Unit,
    onAddButtonClicked: (book: Book) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
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
                    .width(68.dp)
                    .height(96.dp)
                    .shadow(10.dp)
                    .clip(shape = RoundedCornerShape(8, 13, 13, 8))
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .weight(1f)
        ) {
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleLarge,
                color = Sumi

            )

            Text(
                text = book.volumeInfo.authors?.firstOrNull() ?: "Autor desconhecido",
                style = MaterialTheme.typography.titleSmall,
                color = Uguisu
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Amber,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = book.volumeInfo.averageRating.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Sumi
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
                    text = book.volumeInfo.pageCount.toString() + " pages",
                    style = MaterialTheme.typography.labelMedium,
                    color = Uguisu,
                    modifier = Modifier
                        .background(Washi, shape = RoundedCornerShape(25))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        FilledIconButton(
            modifier = Modifier.size(30.dp),
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
                bookCover = BookCover(thumbnail = "https://covers.openlibrary.org/b/isbn/9780099448822-M.jpg"),
                averageRating = 3.5f
            )
        ),
        readingStatus = BookReadingStatus.READ,
        onClick = {},
        onAddButtonClicked = {}
    )
}