package id.pawra.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.home.Activities
import id.pawra.ui.components.home.ListDog
import id.pawra.ui.components.home.Banner
import id.pawra.ui.components.home.NearbyVets
import id.pawra.ui.components.home.Welcome
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.PawraTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController,
    navController: NavController,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    viewModel.getSession()
    val userInfo by viewModel.sessionState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row {
            Welcome(
                image = userInfo.image,
                name = userInfo.name
            )
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Banner()
            ListDog(
                navController = navController,
                viewModel = petViewModel
            )
            NearbyVets(
                navController = navController
            )
            Activities()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    PawraTheme {
        HomeScreen(
            navHomeController = rememberNavController(),
            navController = rememberNavController()
        )
    }
}