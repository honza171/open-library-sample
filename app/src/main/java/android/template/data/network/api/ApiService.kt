package android.template.data.network.api

import android.template.data.network.model.BookDetailsDto
import android.template.data.network.model.BookRecordsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search.json")
    suspend fun getBooks(
        @Query("q") keyword: String,
        @Query("fields") fields: String = "title,key,cover_i",
        @Query("limit") limit: Int = 10,
    ): Result<BookRecordsDto>

    @GET("/works/{id}.json")
    suspend fun getBook(@Path("id") id: String): Result<BookDetailsDto>

    companion object {
        const val BASE_URL = "https://openlibrary.org"
    }
}