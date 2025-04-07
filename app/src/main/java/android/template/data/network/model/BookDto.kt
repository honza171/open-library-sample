package android.template.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val key: String,
    val title: String,
    @SerialName("cover_i")
    val coverId: Long,
)