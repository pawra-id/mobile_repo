package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.data.local.preference.MentalHealthData
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun MentalHealthAction (
    listAction: List<String>,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recommended Actions",
            textAlign = TextAlign.Start,
            modifier = modifier
                .fillMaxWidth(),
            fontFamily = Poppins,
            color = Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
        )

        LazyColumn(
            modifier = modifier
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(listAction) { action ->
                ActionBox(action)
            }
        }

    }
}

@Composable
fun ActionBox(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(LightGreen),
        contentAlignment = Alignment.CenterStart
    ) {
            Text(
                text = value,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(15.dp),
                color = DarkGreen,
                textAlign = TextAlign.Start
            )

    }
}

@Composable
@Preview(showBackground = true)
fun MentalHealthActionPreview() {
    PawraTheme {
        MentalHealthAction(
            listAction = listOf(
                "Take your pet for a walk daily.",
                "Spend quality time playing with your pet.",
                "Consider consulting a veterinarian for professional advice."
            )
        )
    }
}

