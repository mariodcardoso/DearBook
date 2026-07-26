package br.com.mariodias.dearbook.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class BookResponse(
    val totalItems: Int,
    val items: List<Book>
)

@Serializable
data class Book(
    val id: String,
    val volumeInfo: VolumeInfo,
)

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String,
    val authors: List<String>,
    val description: String? = "",
    val pageCount: Int,
    val bookCover: BookCover
)

@Serializable
data class BookCover(
    val thumbnail: String
)