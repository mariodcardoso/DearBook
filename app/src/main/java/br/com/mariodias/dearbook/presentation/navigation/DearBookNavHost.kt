package br.com.mariodias.dearbook.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.mariodias.dearbook.presentation.features.bookdetails.BookDetailsScreen
import br.com.mariodias.dearbook.presentation.features.home.HomeScreen
import br.com.mariodias.dearbook.presentation.features.library.LibraryScreen
import br.com.mariodias.dearbook.presentation.features.searchbook.SearchBooksScreen
import br.com.mariodias.dearbook.presentation.features.settings.SettingsScreen
import br.com.mariodias.dearbook.presentation.features.statistics.StatisticsScreen


@Composable
fun DearBookNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> { HomeScreen() }
        composable<Library> {
            LibraryScreen(
                onBookClick = { bookId -> navController.navigate(BookDetails(bookId)) }
            )
        }
        composable<Statistics> { StatisticsScreen() }
        composable<Settings> { SettingsScreen() }

        composable<Search> {
            SearchBooksScreen(
                onBookClick = { bookId -> navController.navigate(BookDetails(bookId)) }
            )
        }

        composable<BookDetails> { BookDetailsScreen(
            onBackClick = { navController.popBackStack()}
        ) }
    }
}