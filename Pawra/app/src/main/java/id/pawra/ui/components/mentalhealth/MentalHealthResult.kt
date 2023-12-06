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
import id.pawra.data.local.preference.MentalHealthData
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun MentalHealthResult (
    modifier: Modifier = Modifier,
) {
    val mentalHealthData = MentalHealthData(
        "Max",
        70f,
        "There is 70% chance that your dog has depression",
        "Some of the symptoms that the machine catch are sudden change on behavior, eat less food, and aggressiveness toward other animals",
        listOf(
            "Take your pet for a walk daily.",
            "Spend quality time playing with your pet.",
            "Consider consulting a veterinarian for professional advice."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = mentalHealthData.titleResult,
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
            text = mentalHealthData.descResult,
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
        MentalHealthResult()
    }
}