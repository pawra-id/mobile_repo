package id.pawra.ui.screen.pet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.pet.Pet
import id.pawra.ui.components.pet.PetTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        PetTopBar()
        Pet(navController = navController)
    }
}

@Composable
@Preview(showBackground = true)
fun PetScreenPreview() {
    PawraTheme {
        PetScreen(
            navHomeController = rememberNavController(),
            navController = rememberNavController()
        )
    }
}