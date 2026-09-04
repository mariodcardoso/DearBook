package br.com.mariodias.dearbook.presentation.features.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.ui.theme.Ai

@Preview(showBackground = true)
@Composable
fun SettingsScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.penguin),
            contentDescription = null,
            alpha = 0.8f
        )

        Text(
            text = "Coming soon...",
            style = MaterialTheme.typography.headlineLarge,
            color = Ai
        )

    }
}