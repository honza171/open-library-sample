/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.template.data

import android.template.data.network.model.BookDetailsDto
import android.template.data.network.model.BookRecordsDto
import android.template.data.network.api.ApiService
import android.template.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService,
) : Repository {

    override suspend fun getBooks(keyword: String): Result<BookRecordsDto> =
        api.getBooks(keyword = keyword)

    override suspend fun getBookDetails(id: String): Result<BookDetailsDto> =
        api.getBook(id)
}
