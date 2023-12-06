package id.pawra.ui.components.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme

@Composable
fun ListExplore(
    activeMenu: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 10.dp)
    ) {
        if (activeMenu == Menu.MentalHealth.name) {
            (0 until 3).forEach { _ ->
                SharedMental()
            }
        } else {
            (0 until 3).forEach { _ ->
                Blogs(navController = rememberNavController())
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ListExplorePreview() {
    PawraTheme {
        ListExplore(
            ""
        )
    }
}