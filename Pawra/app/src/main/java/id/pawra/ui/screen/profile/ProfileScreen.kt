package id.pawra.ui.screen.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.components.profile.ProfileButton
import id.pawra.ui.components.profile.ProfilePage
import id.pawra.ui.components.profile.ProfileTopBar
import id.pawra.ui.theme.PawraTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController,
    navController: NavController
) {
    Column {
        ProfileTopBar()

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            ProfilePage(viewModel = viewModel(
                factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
            ))

            Spacer(modifier = Modifier.height(28.dp))

            ProfileButton(navController = navController)

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    PawraTheme {
        ProfileScreen(
            navHomeController = rememberNavController(),
            navController = rememberNavController()
        )
    }
}