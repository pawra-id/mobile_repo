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
import id.pawra.data.local.preference.VetData
import id.pawra.ui.components.vetprofile.VetProfile
import id.pawra.ui.components.vetprofile.VetProfileTopBar
import id.pawra.ui.components.vets.Vets
import id.pawra.ui.components.vets.VetsTopBar
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun VetProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        val vetData = VetData(
            "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "drh. Humberto Chavez",
            2.3,
            8,
            9,
            15,
            "Klinik Hewan Purnama",
            "Female",
            75,
            "White, Gold",
            "Jl. Brigjen Sudirman No.5, Purbayan, Kec. Mergangsan, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55131",
            220.2209029,
            2909090.3209302,
            listOf("Doctor of Veterinary Medicine (DVM), University of Veterinary Sciences, 2007", "Bachelor of Science in Animal Science, State University, 2003.")
        )

        VetProfileTopBar()
        VetProfile(
            vet = vetData,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
@Preview(showBackground = true)
fun VetProfileScreenPreview() {
    PawraTheme {
        VetProfileScreen(
            navController = rememberNavController()
        )
    }
}