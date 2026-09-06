package br.com.mariodias.dearbook.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.presentation.components.HomeReadingTile
import br.com.mariodias.dearbook.presentation.components.HomeToReadTile
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Uguisu

@Preview(showBackground = true)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Scaffold(modifier = modifier) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
        ) {

            Header()

            Spacer(Modifier.height(20.dp))

            HomeReadingTile()

            Spacer(Modifier.height(20.dp))

            HomeToReadTile()

            Spacer(Modifier.height(50.dp))
        }
    }
}

@Composable
private fun Header() {
    Column {
        Text(
            text = "Wednesday, august 15".uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = Uguisu
        )

        Text(
            text = "Good morning, Mário",
            style = MaterialTheme.typography.headlineMedium,
            color = Sumi
        )
    }
}