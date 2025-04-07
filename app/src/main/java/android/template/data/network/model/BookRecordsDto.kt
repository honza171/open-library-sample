package android.template.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BookRecordsDto(
    val numFound: Int,
    val docs: List<BookDto>,
)
