package id.pawra.ui.components.petactivities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme

@Composable
fun EmptyPetActivities(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.menu_pet_activities),
            contentDescription = "Empty Activities",
            colorFilter = ColorFilter.tint(LightGray),
            modifier = modifier.size(120.dp)
        )
        Text(
            text = "Activities is empty!",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray,
            modifier = modifier
                .padding(top = 20.dp)
        )
        Text(
            text = "Let's create your dog's activity",
            fontSize = 12.sp,
            color = Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun EmptyPetActivitiesPreview() {
    PawraTheme {
        EmptyPetActivities()
    }
}