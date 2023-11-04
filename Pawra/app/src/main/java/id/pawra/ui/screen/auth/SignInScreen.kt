package id.pawra.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.signin.SignInFooter
import id.pawra.ui.components.signin.SignInForm
import id.pawra.ui.components.signin.SignInHeader
import id.pawra.ui.navigation.Screen

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
    ),
    navController: NavController
) {
    Box(modifier = modifier) {
        var isLoading by remember { mutableStateOf(false) }

        if (isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            var showDialog by remember { mutableStateOf(false) }

            SignInHeader()
            SignInForm(
                viewModel = viewModel,
                showDialog = { showDialog = it },
                navController = navController
            )
            SignInFooter(
                navController = navController
            )

            viewModel.signInState.collectAsState().value.let { userState ->
                when (userState) {
                    is UiState.Loading -> {
                        isLoading = true
                    }
                    is UiState.Success -> {
                        navController.navigate(Screen.Home.route)
                    }
                    is UiState.Error -> {
                        if(showDialog) {
                            ResultDialog(
                                success = false,
                                message = userState.errorMessage,
                                setShowDialog = {
                                    showDialog = it
                                },
                                setLoading = {
                                    isLoading = it
                                }
                            )
                        }
                    }

                    else -> {}
                }

            }
        }
    }
}