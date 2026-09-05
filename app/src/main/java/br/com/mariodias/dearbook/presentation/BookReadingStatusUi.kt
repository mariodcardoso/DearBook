package br.com.mariodias.dearbook.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.ui.theme.Ai
import br.com.mariodias.dearbook.ui.theme.DarkMatcha
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Tsutsuji
import br.com.mariodias.dearbook.ui.theme.Washi

fun getReadingStatusColor(readingStatus: BookReadingStatus?): Color {
    return when (readingStatus) {
        BookReadingStatus.TO_READ -> Tsutsuji
        BookReadingStatus.READING -> DarkMatcha
        BookReadingStatus.READ -> Ai
        else -> Kinari
    }

}

fun getReadingStatusContentColor(readingStatus: BookReadingStatus?): Color {
    return when (readingStatus) {
        BookReadingStatus.TO_READ -> Washi
        BookReadingStatus.READING -> Washi
        BookReadingStatus.READ -> Washi
        else -> Sumi
    }
}

fun getStatusText(readingStatus: BookReadingStatus?): Int {
    return when (readingStatus) {
        BookReadingStatus.TO_READ -> R.string.to_read
        BookReadingStatus.READING -> R.string.reading
        BookReadingStatus.READ -> R.string.read
        else -> R.string.not_in_library
    }
}

fun getReadingStatusIcon(readingStatus: BookReadingStatus?): ImageVector {
    return when(readingStatus) {
        BookReadingStatus.TO_READ -> Icons.Filled.Bookmark
        BookReadingStatus.READING -> Icons.Filled.MenuBook
        BookReadingStatus.READ -> Icons.Filled.CheckCircle
        else -> Icons.Default.AddCircleOutline
    }
}

fun getReadingStatusOutlinedIcon(readingStatus: BookReadingStatus?): ImageVector {
    return when (readingStatus) {
        BookReadingStatus.TO_READ -> Icons.Outlined.BookmarkBorder
        BookReadingStatus.READING -> Icons.Outlined.MenuBook
        BookReadingStatus.READ -> Icons.Outlined.CheckCircle
        else -> Icons.Default.AddCircleOutline
    }
}

