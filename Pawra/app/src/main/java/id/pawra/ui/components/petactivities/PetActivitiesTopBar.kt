package id.pawra.ui.components.petactivities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetActivitiesTopBar(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .background(LightGreen)
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 15.dp),
    ) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Dog Activities",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = DarkGreen,
                modifier = modifier
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PetActivitiesTopBarPreview() {
    PawraTheme {
        PetActivitiesTopBar()
    }
}