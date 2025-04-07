package android.template.ui.features.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import android.template.R
import android.template.domain.model.BookDetails
import android.template.ui.component.AppToolbar
import android.template.ui.component.Toast
import android.template.ui.features.detail.DetailsViewModel.UiState.Success
import android.template.ui.theme.AppTheme
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel) {
    val bookListUpdateState by detailsViewModel.updateState.collectAsState()

    Scaffold(
        topBar = { AppToolbar(title = stringResource(id = R.string.book_detail)) }
    ) { padding ->
        Column(Modifier.padding(padding)) {

            when (val state = bookListUpdateState) {
                DetailsViewModel.UiState.ErrorFromAPI -> Toast(R.string.api_error)
                DetailsViewModel.UiState.LoadingFromAPI -> Unit
                is Success -> BookDetails(state)
            }
        }
    }
}

@Composable
fun BookDetails(state: Success, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Card(
            Modifier
                .heightIn(min = 48.dp)
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.book_title, state.book.title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview
@Composable
private fun BookDetailsPreview() {
    AppTheme {
        BookDetails(
            state = Success(
                BookDetails(
                    title = "Preview"
                )
            )
        )
    }
}