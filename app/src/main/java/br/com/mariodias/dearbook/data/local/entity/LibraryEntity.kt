package br.com.mariodias.dearbook.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import br.com.mariodias.dearbook.domain.model.BookReadingStatus


@Entity
data class LibraryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val status: BookReadingStatus,
    val isInLibrary: Boolean,
    val addedAt: Long,
    val synced: Boolean

)