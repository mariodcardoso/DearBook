package br.com.mariodias.dearbook.presentation.features.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.domain.model.Book
import br.com.mariodias.dearbook.domain.model.BookCover
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.domain.model.LibraryBook
import br.com.mariodias.dearbook.domain.model.VolumeInfo
import coil3.compose.AsyncImage

@Composable
fun ItemLibraryBookView(libraryBook: LibraryBook) {

    ItemLibraryBookViewContent(libraryBook)

}

@Composable
fun ItemLibraryBookViewContent(libraryBook: LibraryBook) {

    Box() {
        AsyncImage(
            model = libraryBook.book.volumeInfo.bookCover.thumbnail,
            contentDescription = libraryBook.book.volumeInfo.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(8.dp))
        )

        Icon(
            imageVector = Icons.Filled.Bookmark,
            contentDescription = null,
            tint = libraryBook.readingStatus.color,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(36.dp)
                .offset(x = (-3).dp, y = (-7).dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ItemLibraryBookViewContentPreview() {

    val libraryBook = LibraryBook(
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
    )

    ItemLibraryBookViewContent(libraryBook)

}