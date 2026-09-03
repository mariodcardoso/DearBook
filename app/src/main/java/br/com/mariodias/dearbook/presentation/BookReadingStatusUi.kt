package br.com.mariodias.dearbook.presentation

import androidx.compose.ui.graphics.Color
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.ui.theme.Ai
import br.com.mariodias.dearbook.ui.theme.DarkMatcha
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Tsutsuji
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Washi

fun getStatusColor(readingStatus: BookReadingStatus?): Color {
    return when (readingStatus) {
        BookReadingStatus.TO_READ -> Tsutsuji
        BookReadingStatus.READING -> DarkMatcha
        BookReadingStatus.READ -> Ai
        else -> Kinari
    }

}

fun getStatusTextColor(readingStatus: BookReadingStatus?): Color {
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

