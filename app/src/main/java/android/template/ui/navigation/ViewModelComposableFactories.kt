@file:Suppress("MatchingDeclarationName")
package android.template.ui.navigation

import android.app.Activity
import android.template.ui.MainActivity
import android.template.ui.features.detail.DetailsViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.EntryPointAccessors

interface BaseViewModelFactoryProvider {

    fun getDetailsViewModelFactory(): DetailsViewModel.Factory
}

@Composable
fun detailViewModel(bookId: String): DetailsViewModel = viewModel(
    factory = DetailsViewModel.provideFactory(
        getViewModelFactoryProvider().getDetailsViewModelFactory(),
        bookId
    )
)

@Composable
private fun getViewModelFactoryProvider() = EntryPointAccessors.fromActivity(
    activity = LocalContext.current as Activity,
    entryPoint = MainActivity.ViewModelFactoryProvider::class.java
)
