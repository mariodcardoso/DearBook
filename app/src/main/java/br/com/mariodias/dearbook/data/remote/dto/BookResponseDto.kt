package br.com.mariodias.dearbook.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BookResponseDto(
    @SerialName("totalItems")
    val totalItems: Int,
    @SerialName("items")
    val items: List<BookDto>
)

@Serializable
data class BookDto(
    @SerialName("id")
    val id: String,

    @SerialName("volumeInfo")
    val volumeInfo: VolumeInfoDto,
)


@Serializable
data class VolumeInfoDto(
    @SerialName("title")
    val title: String? = "",

    @SerialName("subtitle")
    val subtitle: String? = "",

    @SerialName("authors")
    val authors: List<String>? = emptyList(),

    @SerialName("description")
    val description: String? = "",

    @SerialName("pageCount")
    val pageCount: Int? = 0,

    @SerialName("imageLinks")
    val bookCover: BookCoverDto? = BookCoverDto()
)

@Serializable
data class BookCoverDto(
    @SerialName("thumbnail")
    val thumbnail: String? = ""
)