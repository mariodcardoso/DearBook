package br.com.mariodias.dearbook.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.ui.theme.Border
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Spacing
import br.com.mariodias.dearbook.ui.theme.Washi

@Composable
fun DearBookBottomBar(
    currentDestination: NavDestination?,
    onItemClick: (DearBookNav) -> Unit
) {
    val leftItem = listOf(Home, Library)
    val rightItem = listOf(Statistics, Settings)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Border)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = Spacing.xxl, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                leftItem.forEach { item ->
                    val isSelected = currentDestination?.hasRoute(item::class) == true

                    val tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    Column(
                        modifier = Modifier.clickable { onItemClick(item) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            item.icon,
                            modifier = Modifier.size(36.dp),
                            contentDescription = stringResource(item.label),
                            tint = tint
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(56.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                rightItem.forEach { item ->
                    val isSelected = currentDestination?.hasRoute(item::class) == true

                    val tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    Column(
                        modifier = Modifier.clickable { onItemClick(item) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            item.icon,
                            modifier = Modifier.size(36.dp),
                            contentDescription = stringResource(item.label),
                            tint = tint
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-35).dp)
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(10.dp, color = Washi, shape = RoundedCornerShape(100))
                .clickable { onItemClick(Search) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(R.string.search_action_description),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

