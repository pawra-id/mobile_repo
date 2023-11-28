package id.pawra.ui.screen.pet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.petactivities.PetActivities
import id.pawra.ui.components.petactivities.PetActivitiesTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetActivitiesScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PetActivitiesTopBar(navController = navController)
        PetActivities()
    }

}

@Composable
@Preview(showBackground = true)
fun PetActivitiesScreenPreview() {
    PawraTheme {
        PetActivitiesScreen(navController = rememberNavController())
    }
}