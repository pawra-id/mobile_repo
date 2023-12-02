package id.pawra.ui.screen.pet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.PetData
import id.pawra.di.Injection
import id.pawra.ui.components.petprofile.PetProfile
import id.pawra.ui.components.petprofile.PetProfileTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column {
        PetProfileTopBar(navController = navController)

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            val petData = PetData(
                "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
                "Max",
                "Golden Retrivier",
                true,
                1,
                24,
                "Male",
                75,
                "White, Gold",
                "123456",
                "A playful and friendly Corgi."
            )

            PetProfile(
                image = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos:5121efa9bf4f08285ea0d098ce7756aa20230924195603.png",
                pet = petData,
                viewModel = viewModel(
                    factory = ViewModelFactory(LocalContext.current)
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun PetProfileScreenPreview() {
    PawraTheme {
        PetProfileScreen(
            navController = rememberNavController()
        )
    }
}