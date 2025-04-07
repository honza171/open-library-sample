package android.template.domain

import android.template.data.network.model.BookDetailsDto
import android.template.data.network.model.BookRecordsDto
import kotlinx.coroutines.flow.MutableStateFlow

interface Repository {

    suspend fun getBooks(keyword: String = "Hemingway"): Result<BookRecordsDto>

    suspend fun getBookDetails(id: String): Result<BookDetailsDto>
}