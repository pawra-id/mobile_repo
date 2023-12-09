package id.pawra.ui.screen.vet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.pet.Pet
import id.pawra.ui.components.pet.PetTopBar
import id.pawra.ui.components.vets.Vets
import id.pawra.ui.components.vets.VetsTopBar
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun VetScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        VetsTopBar()
        Vets(navController = navController)
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