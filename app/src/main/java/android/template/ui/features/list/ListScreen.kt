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

import android.annotation.SuppressLint
import android.template.R
import android.template.domain.model.Book
import android.template.ui.component.AppToolbar
import android.template.ui.component.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import android.template.ui.features.list.ListViewModel.ListScreenUiState.*
import android.template.ui.features.list.ListViewModel.ListScreenUiState
import android.template.ui.theme.AppTheme
import android.template.ui.theme.Typography
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    listViewModel: ListViewModel,
    openDetailsClicked: (String) -> Unit,
) {
    val bookListUpdateState by listViewModel.state.collectAsState()

    Scaffold(
        topBar = { AppToolbar(title = stringResource(id = R.string.book_list)) }
    ) { padding ->
        Column(Modifier.padding(padding)) {

            when (val state = bookListUpdateState) {
                ErrorFromAPI -> ErrorFromApi()
                is UpdateSuccess, LoadingFromAPI -> ListBooks(
                    state = state,
                    onRefresh = listViewModel::refresh,
                    onDetailsClicked = openDetailsClicked,
                )
            }
        }
    }
}

@Composable
@Suppress("DEPRECATION")
private fun ListBooks(
    state: ListScreenUiState,
    onRefresh: () -> Unit,
    onDetailsClicked: (String) -> Unit,
) = SwipeRefresh(
    state = rememberSwipeRefreshState(state == LoadingFromAPI),
    onRefresh = onRefresh,
    modifier = Modifier
        .scrollable(rememberScrollState(), Orientation.Vertical)
        .systemBarsPadding()
        .padding(16.dp)
) {
    var books by remember { mutableStateOf(emptyList<Book>()) }
    (state as? UpdateSuccess)?.let {
        books = it.books
    }

    Column {
        LazyColumn {
            itemsIndexed(books) { _, book ->
                Card(
                    Modifier
                        .heightIn(min = 48.dp)
                        .padding(vertical = 4.dp)
                        .fillParentMaxWidth()
                        .clickable { onDetailsClicked(book.key) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = book.title, style = Typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorFromApi() = Toast(R.string.api_error)

@Preview
@Composable
private fun ListScreenPreview() {
    AppTheme {
        ListBooks(
            state = UpdateSuccess(
                (1..10).map {
                    Book(it.toString(), "Preview $it")
                }
            ),
            onRefresh = {},
            onDetailsClicked = {},
        )
    }
}
