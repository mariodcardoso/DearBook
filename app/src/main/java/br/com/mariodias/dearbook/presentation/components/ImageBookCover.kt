package br.com.mariodias.dearbook.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.R
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.ui.theme.Washi
import coil3.compose.AsyncImage

@Composable
fun ImageBookCover(
    modifier: Modifier = Modifier,
    image: String = "",
    showRibbonStatus: BookReadingStatus? = null
) {
    Box {
        AsyncImage(
            model = image,
            contentDescription = stringResource(R.string.book_cover_description),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

        if (showRibbonStatus != null) {
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = null,
                    tint = Washi,
                    modifier = Modifier
                        .size(31.dp)
                        .offset(x = (-1.5).dp, y = (-4.5).dp)
                )
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = null,
                    tint = getReadingStatusColor(showRibbonStatus),
                    modifier = Modifier
                        .size(28.dp)
                        .offset(y = (-3).dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageBookCoverPreview() {

    Box(Modifier.padding(16.dp)) {
        ImageBookCover(
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(66f / 106f)
                .shadow(10.dp)
                .clip(shape = RoundedCornerShape(8, 13, 13, 8)),
            showRibbonStatus = BookReadingStatus.TO_READ
        )
    }

}