package android.template.ui.navigation

import android.template.ui.features.detail.DetailsScreen
import android.template.ui.features.list.ListScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = NavRoutes.List.path) {

        composable(NavRoutes.List.path) {
            ListScreen(hiltViewModel(), openDetailsClicked = {
                navController.navigate(NavRoutes.Details.build(it))
            })
        }

        composable(NavRoutes.Details.path) { backStackEntry ->
            backStackEntry.arguments?.getString(NavRoutes.DETAILS_ID_KEY)?.let {
                DetailsScreen(detailViewModel(bookId = it))
            }
        }
    }
}
