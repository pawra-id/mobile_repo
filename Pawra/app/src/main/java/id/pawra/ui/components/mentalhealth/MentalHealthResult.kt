package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun MentalHealthResult (
    modifier: Modifier = Modifier,
    percentage: Float,
    descriptionResult: String
) {
    val titleResult = "There is ${percentage}% chance that your dog has depression"

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = titleResult,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            fontFamily = Poppins,
            color = Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = descriptionResult,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            fontFamily = Poppins,
            color = Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MentalHealthResultPreview() {
    PawraTheme {
        MentalHealthResult(
            percentage = 0f,
            descriptionResult = ""
        )
    }
}