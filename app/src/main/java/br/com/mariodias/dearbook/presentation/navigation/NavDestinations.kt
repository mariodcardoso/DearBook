package br.com.mariodias.dearbook.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.mariodias.dearbook.R
import kotlinx.serialization.Serializable

sealed interface DearBookNav

interface BottomNavDestinations : DearBookNav {
    val icon: ImageVector
    val label: Int
}

@Serializable
data object Home : BottomNavDestinations {
    override val icon = Icons.Default.Home
    override val label = R.string.nav_home
}

@Serializable
data object Library : BottomNavDestinations {
    override val icon = Icons.Default.Book
    override val label = R.string.nav_library
}

@Serializable
data object Statistics : BottomNavDestinations {
    override val icon = Icons.Default.BarChart
    override val label = R.string.nav_statistics
}

@Serializable
data object Settings : BottomNavDestinations{
    override val icon = Icons.Default.Settings
    override val label = R.string.nav_settings
}

@Serializable
data object Search : DearBookNav

@Serializable
data class BookDetails(val bookId: String): DearBookNav