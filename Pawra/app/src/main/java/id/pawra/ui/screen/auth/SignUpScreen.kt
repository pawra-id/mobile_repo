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
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.components.signup.SignUpFooter
import id.pawra.ui.components.signup.SignUpForm
import id.pawra.ui.components.signup.SignUpHeader

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navController: NavController
) {
    Box(modifier = modifier) {
        var isLoading by remember { mutableStateOf(false) }

        if (isLoading) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()
            ) {
                LoadingBox()
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

            SignUpHeader()
            SignUpForm(
                viewModel = viewModel,
                showDialog = { showDialog = it }
            )
            SignUpFooter(
                navController = navController
            )

            viewModel.signUpState.collectAsState().value.let { userState ->
                when (userState) {
                    is UiState.Loading -> {
                        isLoading = true
                    }
                    is UiState.Success -> {
                        if(showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = true,
                                message = "User created successfully",
                                setShowDialog = {
                                    showDialog = it
                                }
                            )
                        }
                    }
                    is UiState.Error -> {
                        if(showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = false,
                                message = userState.errorMessage,
                                setShowDialog = {
                                    showDialog = it
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