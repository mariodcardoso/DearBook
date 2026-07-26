package br.com.mariodias.dearbook.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import br.com.mariodias.dearbook.presentation.bookdetails.BookDetailsScreen
import br.com.mariodias.dearbook.presentation.searchbook.SearchBooksScreen


@Composable
fun DearBookNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchBooksScreen(
                onBookClick = { bookId -> navController.navigate("bookDetails/$bookId") }
            )
        }
        composable(
            route = "bookDetails/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) {
            BookDetailsScreen()
        }
    }
}