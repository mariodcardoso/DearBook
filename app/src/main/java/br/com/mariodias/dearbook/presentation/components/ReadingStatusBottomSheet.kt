package br.com.mariodias.dearbook.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.mariodias.dearbook.domain.model.BookReadingStatus
import br.com.mariodias.dearbook.presentation.getReadingStatusColor
import br.com.mariodias.dearbook.presentation.getReadingStatusIcon
import br.com.mariodias.dearbook.presentation.getReadingStatusOutlinedIcon
import br.com.mariodias.dearbook.presentation.getStatusText
import br.com.mariodias.dearbook.ui.theme.Kinari
import br.com.mariodias.dearbook.ui.theme.Momiji
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingStatusBottomSheet(
    isBookInLibrary: Boolean,
    readingStatusSelected: BookReadingStatus?,
    onReadingBookStatusClicked: (BookReadingStatus) -> Unit,
    onRemoveBookFromLibrary: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { closeBottomSheet(scope, sheetState) { onDismissRequest() } },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {

        Column(
            Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Reading Status",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )

            BookReadingStatus.entries.forEach { status ->
                ItemBottomSheetView(status, readingStatusSelected) {
                    onReadingBookStatusClicked(status)
                    closeBottomSheet(scope, sheetState, { onDismissRequest() })
                }
            }

            if (isBookInLibrary) {
                HorizontalDivider(
                    modifier = Modifier.padding(12.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                onRemoveBookFromLibrary()
                                closeBottomSheet(
                                    scope, sheetState, { onDismissRequest() })
                            }),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove",
                        tint = Momiji
                    )

                    Text(
                        text = "Remove from library",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Momiji
                    )

                }
            }
        }
    }
}

@Composable
fun ItemBottomSheetView(
    readingStatus: BookReadingStatus = BookReadingStatus.TO_READ,
    readingStatusSelected: BookReadingStatus?,
    onAddToLibraryClick: (BookReadingStatus) -> Unit
) {

    val isSelected = readingStatus == readingStatusSelected

    val statusColor = if (isSelected) getReadingStatusColor(readingStatus) else Kinari
    val statusText = getStatusText(readingStatus)

    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(statusColor.copy(alpha = 0.1f))
            .border(1.dp, statusColor, shape = RoundedCornerShape(15.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = statusColor.copy(alpha = 0.7f)),
                onClick = { onAddToLibraryClick(readingStatus) }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Icon(
            imageVector = if (isSelected) getReadingStatusIcon(readingStatusSelected) else getReadingStatusOutlinedIcon(readingStatus),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = statusColor
        )

        Text(
            text = stringResource(statusText),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = statusColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun closeBottomSheet(scope: CoroutineScope, sheetState: SheetState, onHidden: () -> Unit) {
    scope.launch { sheetState.hide() }
        .invokeOnCompletion {
            if (!sheetState.isVisible) {
                onHidden()
            }
        }
}