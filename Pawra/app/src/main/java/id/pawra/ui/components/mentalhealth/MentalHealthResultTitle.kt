package id.pawra.ui.components.mentalhealth

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
import id.pawra.data.local.preference.MentalHealthData
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun MentalHealthResultTitle (
    modifier: Modifier = Modifier
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

    Column {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = LightGreen)) {
                    append(mentalHealthData.petName)
                }
                withStyle(style = SpanStyle(color = Black)) {
                    append(" mental")
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
                    append("health result")
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
fun MentalHealthResultTitlePreview() {
    PawraTheme {
        MentalHealthResultTitle()
    }
}