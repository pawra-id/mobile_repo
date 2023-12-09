package id.pawra.ui.components.addactivities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.White
import kotlinx.coroutines.delay
import java.util.UUID

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