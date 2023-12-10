package id.pawra.ui.screen.vet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.pet.Pet
import id.pawra.ui.components.pet.PetTopBar
import id.pawra.ui.components.vets.Vets
import id.pawra.ui.components.vets.VetsTopBar
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun VetScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    vetViewModel: VetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        VetsTopBar()
        Vets(
            navController = navController,
            vetViewModel = vetViewModel
        )
    }
}

@Composable
@Preview(showBackground = true)
fun VetScreenPreview() {
    PawraTheme {
        VetScreen(
            navController = rememberNavController()
        )
    }
}