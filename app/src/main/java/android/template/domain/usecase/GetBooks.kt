package android.template.domain.usecase

import android.template.domain.Repository
import android.template.domain.model.Book
import android.template.domain.model.toModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBooks @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(): Result<List<Book>> {
        return repository.getBooks()
            .map { it.docs }
            .map { bookList -> bookList.map { it.toModel() } }
    }
}