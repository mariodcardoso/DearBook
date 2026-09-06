package br.com.mariodias.dearbook.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Surface
import br.com.mariodias.dearbook.ui.theme.Tsutsuji
import br.com.mariodias.dearbook.ui.theme.Uguisu

@Composable
fun ReadingStatusTile() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, color = Kinari),
        colors = CardDefaults.cardColors(Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 14.dp)
        ) {

            ReadingStatusHeader()

            BookInfoRow()

        }
    }
}

@Composable
private fun ReadingStatusHeader() {

    val dotAlpha by rememberBlinkingAlpha()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = "Filter",
            tint = Tsutsuji,
            modifier = Modifier
                .size(7.dp)
                .alpha(dotAlpha)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "Proxima leitura".uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = Tsutsuji,
        )

        Spacer(modifier = Modifier.weight(1f))

        AssistChip(
            onClick = {},
            label = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Sort,
                    contentDescription = "Filter",
                    modifier = Modifier.size(14.dp),
                    tint = Tsutsuji
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Sort",
                    style = MaterialTheme.typography.labelMedium,
                    color = Tsutsuji
                )
            },
            shape = RoundedCornerShape(100),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Tsutsuji.copy(alpha = 0.12f)
            ),
            border = BorderStroke(0.dp, color = Color.Unspecified)
        )

    }
}

@Composable
private fun rememberBlinkingAlpha(): State<Float> {
    val transition = rememberInfiniteTransition(label = "dot-blink")
    return transition.animateFloat(
        initialValue = 1f, targetValue = 0.2f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "dot-alpha"
    )
}

@Composable
private fun ReadingStatusDetails() {
    Text(
        text = "The Ikigai Journey",
        style = MaterialTheme.typography.headlineSmall,
        color = Sumi
    )

    Text(
        text = "Hector Garcia",
        style = MaterialTheme.typography.titleSmall,
        color = Uguisu
    )

    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Guia prático para encontrar seu propósito e transformar o cotidiano",
        style = MaterialTheme.typography.bodySmall,
        color = Uguisu
    )
}

@Composable
private fun BookInfoRow() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ImageBookCover(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(66f / 106f)
                .shadow(10.dp)
                .clip(shape = RoundedCornerShape(8, 13, 13, 8)),
            showRibbonStatus = BookReadingStatus.TO_READ
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {

            ReadingStatusDetails()

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(6.dp))

            ReadingStatusFooter()

        }
    }
}

@Composable
private fun ReadingStatusFooter() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Adicionado a 3 dias",
            style = MaterialTheme.typography.labelMedium,
            color = Uguisu
        )

        Text(
            text = "Iniciar leitura >",
            style = MaterialTheme.typography.labelLarge,
            color = Tsutsuji
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingStatusTilePreview() {
    Box(
        Modifier.padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        ReadingStatusTile()
    }
}