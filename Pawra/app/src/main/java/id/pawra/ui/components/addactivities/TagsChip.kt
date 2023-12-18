package id.pawra.ui.components.addactivities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.White

@Immutable
data class ChipData(
    val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsChip(
    data: ChipData,
    onDeleteClick: () -> Unit
) {
    InputChip(
        modifier = Modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = {},
        selected = true,
        border = InputChipDefaults.inputChipBorder(selectedBorderColor = DarkGreen, borderWidth = 4.dp),
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = LightGreen,
            selectedLabelColor = DarkGreen
        ),
        label = {
            Text(
                text = data.text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = DarkGreen,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onDeleteClick()
                    }
                    .background(DarkGreen.copy(alpha = .4f))
                    .size(16.dp)
                    .padding(2.dp),
                imageVector = Icons.Filled.Close,
                tint = White,
                contentDescription = null
            )
        }
    )
}