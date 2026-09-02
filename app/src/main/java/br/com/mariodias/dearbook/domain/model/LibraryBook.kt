package br.com.mariodias.dearbook.domain.model

import androidx.compose.ui.graphics.Color
import br.com.mariodias.dearbook.ui.theme.Ai
import br.com.mariodias.dearbook.ui.theme.Matcha
import br.com.mariodias.dearbook.ui.theme.Sakura


data class LibraryBook(
    val book: Book,
    val readingStatus: BookReadingStatus,
    val addedAt: Long
)

enum class BookReadingStatus(val color: Color){
    TO_READ(Sakura),
    READING(Matcha),
    READ(Ai)
}