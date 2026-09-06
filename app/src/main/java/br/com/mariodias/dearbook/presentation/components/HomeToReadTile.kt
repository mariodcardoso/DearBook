package br.com.mariodias.dearbook.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
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
import br.com.mariodias.dearbook.ui.theme.Washi

@Preview(showBackground = true)
@Composable
fun HomeToReadTilePreview() {
    Column(
        Modifier
            .padding(14.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        HomeToReadTile()
    }
}

@Composable
fun HomeToReadTile() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, color = Kinari),
        colors = CardDefaults.cardColors(Gofun),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            HomeTileHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                BookReadingStatus.TO_READ
            )

            Card(
                modifier = Modifier.padding(2.dp),
                border = BorderStroke(1.dp, color = Kinari),
                colors = CardDefaults.cardColors(Washi),
            ) {
                BookInfoRow()
            }

        }
    }
}

@Composable
private fun BookInfoRow() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
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

            TileFooter()
        }
    }
}

@Composable
private fun ReadingStatusDetails() {
    Text(
        text = "The Ikigai Journey",
        style = MaterialTheme.typography.titleLarge,
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
        color = Uguisu,
        fontStyle = FontStyle.Italic
    )
}

@Composable
private fun TileFooter() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        AssistChip(
            onClick = {},
            label = {

                Text(
                    text = stringResource(R.string.home_tile_to_read_footer_start_reading),
                    style = MaterialTheme.typography.labelMedium,
                    color = getReadingStatusColor(BookReadingStatus.TO_READ),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = getReadingStatusColor(BookReadingStatus.TO_READ)
                )
            },
            shape = RoundedCornerShape(100),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = getReadingStatusColor(BookReadingStatus.TO_READ).copy(alpha = 0.12f)
            ),
            border = BorderStroke(0.dp, color = Color.Unspecified)
        )
    }

}