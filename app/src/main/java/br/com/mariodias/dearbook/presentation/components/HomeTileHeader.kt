package br.com.mariodias.dearbook.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.presentation.getReadingStatusColor

@Composable
fun HomeTileHeader(
    modifier: Modifier = Modifier,
    readingStatus: BookReadingStatus
) {

    val dotAlpha by rememberBlinkingAlpha()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = null,
            tint = getReadingStatusColor(readingStatus),
            modifier = Modifier
                .size(7.dp)
                .alpha(dotAlpha)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = getReadingStatusHeaderText(readingStatus).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = getReadingStatusColor(readingStatus),
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.weight(1f))

        AssistChip(
            onClick = {},
            label = {
                Icon(
                    imageVector = getHeaderChipIcon(readingStatus),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = getReadingStatusColor(readingStatus)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = getChipText(readingStatus),
                    style = MaterialTheme.typography.labelMedium,
                    color = getReadingStatusColor(readingStatus)
                )
            },
            shape = RoundedCornerShape(100),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = getReadingStatusColor(readingStatus).copy(alpha = 0.12f)
            ),
            border = BorderStroke(0.dp, color = Color.Unspecified)
        )

    }
}

@Composable
private fun getReadingStatusHeaderText(readingStatus: BookReadingStatus): String {

    return when (readingStatus) {
        BookReadingStatus.TO_READ -> stringResource(R.string.home_tile_to_read_header_status)
        BookReadingStatus.READING -> stringResource(R.string.home_tile_reading_header_status)
        else -> ""
    }

}

@Composable
private fun getChipText(readingStatus: BookReadingStatus): String {

    return when (readingStatus) {
        BookReadingStatus.TO_READ -> stringResource(R.string.home_tile_to_read_chip_text)
        BookReadingStatus.READING -> stringResource(R.string.home_tile_reading_chip_text)
        else -> ""
    }

}

@Composable
private fun getHeaderChipIcon(readingStatus: BookReadingStatus): ImageVector {

    return when (readingStatus) {
        BookReadingStatus.TO_READ -> Icons.AutoMirrored.Filled.Sort
        else -> Icons.Default.Check
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