package android.template.domain.usecase

import android.util.Log
import android.template.domain.Repository
import android.template.domain.model.BookDetails
import android.template.domain.model.toModel
import javax.inject.Inject

class GetBook @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(id: String): Result<BookDetails> {
        return repository.getBookDetails(id).map { bookDetails ->
            Log.d("GetBook", "Title: " + bookDetails.title)
            bookDetails.toModel()
        }
    }
}