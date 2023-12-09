package id.pawra.ui.components.addactivities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun ActivitiesPrevTitle(
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Black)) {
                    append("My")
                }
                withStyle(style = SpanStyle(color = LightGreen)) {
                    append(" Dog")
                }
            },
            modifier = modifier
                .fillMaxWidth(),
            fontFamily = Poppins,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Black)) {
                    append("Activity")
                }
            },
            modifier = modifier
                .fillMaxWidth(),
            fontFamily = Poppins,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ActivitiesPrevTitlePreview() {
    PawraTheme {
        ActivitiesPrevTitle()
    }
}