package id.pawra.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.dialog.ConfirmationDialog
import id.pawra.ui.components.profile.ProfileButton
import id.pawra.ui.components.profile.ProfilePage
import id.pawra.ui.components.profile.ProfileTopBar
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.PawraTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

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

            ProfilePage(viewModel = viewModel)

            Spacer(modifier = Modifier.height(28.dp))

            ProfileButton(
                navController = navController,
                setShowDialog = {
                    showDeleteConfirmation = it
                }
            )

            Spacer(modifier = Modifier.height(35.dp))
        }
    }

    if (showDeleteConfirmation) {
        ConfirmationDialog(
            headText = "Logout Confirmation",
            warn = false,
            message = "Are you sure you want to logout?",
            yesText = "Yes, logout",
            cancelText = "Cancel",
            setShowDialog = { showDeleteConfirmation = it },
            action = {
                viewModel.logout()
                navController.navigate(Screen.SignIn.route){
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    PawraTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}