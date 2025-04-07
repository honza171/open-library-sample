package android.template.domain.model

import android.template.data.network.model.BookDetailsDto

data class BookDetails(
    val title: String
)

fun BookDetailsDto.toModel(): BookDetails = BookDetails(
    title = title
)
