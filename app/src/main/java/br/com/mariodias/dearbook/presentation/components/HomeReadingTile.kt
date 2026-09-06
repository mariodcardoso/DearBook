package br.com.mariodias.dearbook.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.ui.theme.Gofun
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Sumi
import br.com.mariodias.dearbook.ui.theme.Uguisu

@Preview(showBackground = true)
@Composable
fun HomeReadingTilePreview() {
    HomeReadingTile()
}

@Composable
fun HomeReadingTile() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, color = Kinari),
        colors = CardDefaults.cardColors(Gofun),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {

            HomeTileHeader(
                modifier = Modifier.fillMaxWidth(),
                BookReadingStatus.READING
            )

            BookInfoRow()

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider()

            ReadingTileFooter()
        }
    }
}

@Composable
private fun BookInfoRow() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
    ) {

        ImageBookCover(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(66f / 106f)
                .shadow(10.dp)
                .clip(shape = RoundedCornerShape(8, 13, 13, 8)),
            showRibbonStatus = BookReadingStatus.READING
        )

        ReadingStatusDetails()
    }
}

@Composable
private fun ReadingStatusDetails() {

    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxHeight()
    ) {

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

        Spacer(modifier = Modifier.weight(1f))

        ReadingProgress()

        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
private fun ReadingProgress() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Page 112 of 180",
            style = MaterialTheme.typography.labelSmall,
            color = Sumi
        )

        Text(
            text = "62 %",
            style = MaterialTheme.typography.labelSmall,
            color = Sumi
        )
    }

    Spacer(Modifier.height(6.dp))

    LinearProgressIndicator(
        progress = { 0.62f },
        modifier = Modifier
            .height(12.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun ReadingTileFooter() {

    val statusColor = getReadingStatusColor(BookReadingStatus.READING)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccessTime,
            contentDescription = null,
            tint = Uguisu,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = stringResource(R.string.home_tile_reading_footer_added_message),
            style = MaterialTheme.typography.labelMedium,
            color = Uguisu,
            modifier = Modifier.weight(1f)
        )

        AssistChip(
            onClick = {},
            label = {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = statusColor
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(R.string.home_tile_reading_footer_log_pages),
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor,
                    fontWeight = FontWeight.Bold
                )
            },
            shape = RoundedCornerShape(100),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = statusColor.copy(alpha = 0.12f)
            ),
            border = BorderStroke(0.dp, color = Color.Unspecified)
        )
    }

}

