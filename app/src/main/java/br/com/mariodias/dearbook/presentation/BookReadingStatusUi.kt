package br.com.mariodias.dearbook.presentation

import androidx.compose.ui.graphics.Color
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.ui.theme.Ai
import br.com.mariodias.dearbook.ui.theme.Matcha
import br.com.mariodias.dearbook.ui.theme.Sakura

fun getStatusColor(readingStatus: BookReadingStatus): Color {

    return when (readingStatus) {
        BookReadingStatus.TO_READ -> Sakura
        BookReadingStatus.READING -> Matcha
        BookReadingStatus.READ -> Ai
    }
}

fun getStatusText(readingStatus: BookReadingStatus): Int {

    return when (readingStatus) {
        BookReadingStatus.TO_READ -> R.string.to_read
        BookReadingStatus.READING -> R.string.reading
        BookReadingStatus.READ -> R.string.read
    }
}