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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.petprofile.PetProfileTopBar
import id.pawra.ui.components.profile.ProfileTopBar
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
            verticalArrangement = Arrangement.Center,
        ) {

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