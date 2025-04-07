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

package android.template.ui.features.list

import android.template.domain.model.Book
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import android.template.domain.usecase.GetBooks
import android.template.utils.PublishFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import android.template.ui.features.list.ListViewModel.ListScreenUiState.ErrorFromAPI
import android.template.ui.features.list.ListViewModel.ListScreenUiState.LoadingFromAPI
import android.template.ui.features.list.ListViewModel.ListScreenUiState.UpdateSuccess
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getBooks: GetBooks
) : ViewModel() {

    private val events = PublishFlow<Event>()

    private val _state = MutableStateFlow<ListScreenUiState>(LoadingFromAPI)
    val state = _state.asStateFlow()

    init {
        events
            .filterIsInstance<Event.Refresh>()
            .onStart { updateBookList() }
            .onEach {
                _state.value = LoadingFromAPI
                updateBookList()
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch { events.emit(Event.Refresh) }
    }

    private suspend fun updateBookList() {
        getBooks()
            .onSuccess { _state.value = UpdateSuccess(it) }
            .onFailure { _state.value = ErrorFromAPI }
    }

    private sealed class Event {
        object Refresh : Event()
    }

    sealed interface ListScreenUiState {
        object LoadingFromAPI : ListScreenUiState
        data class UpdateSuccess(val books: List<Book>) : ListScreenUiState
        object ErrorFromAPI : ListScreenUiState
    }
}

