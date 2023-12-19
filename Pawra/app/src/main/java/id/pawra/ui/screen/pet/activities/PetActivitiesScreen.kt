package id.pawra.ui.screen.pet.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.petactivities.PetActivities
import id.pawra.ui.components.petactivities.PetActivitiesTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetActivitiesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petId: Int,
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PetActivitiesTopBar()

        PetActivities(
            navController = navController,
            activitiesViewModel = activitiesViewModel,
            petId = petId,
        )
    }

}

@Composable
@Preview(showBackground = true)
fun PetActivitiesScreenPreview() {
    PawraTheme {
        PetActivitiesScreen(
            navController = rememberNavController(),
            petId = 0
        )
    }
}