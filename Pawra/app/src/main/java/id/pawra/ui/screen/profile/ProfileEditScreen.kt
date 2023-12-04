package id.pawra.ui.screen.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.components.profile.ProfileEditForm
import id.pawra.ui.components.profile.ProfileEditTopBar
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    navController: NavController
) {
    Column {
        ProfileEditTopBar()
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = modifier) {
            var isLoading by remember { mutableStateOf(false) }
            var showDialog by remember { mutableStateOf(false) }

            if (isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
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
                verticalArrangement = Arrangement.Top,
            ) {
                ProfileEditForm(viewModel = viewModel,
                    showDialog = { showDialog = it },
                    navController = navController)
            }

            viewModel.updateProfileState.collectAsState().value.let { userState ->
                when (userState) {
                    is UiState.Loading -> {
                        isLoading = true
                    }
                    is UiState.Success -> {
                        if(showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = true,
                                message = "Update successfully",
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

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ProfileEditScreenPreview() {
    PawraTheme {
        ProfileEditScreen(viewModel = viewModel(
            factory = ViewModelFactory(LocalContext.current)
        ),
            navController = rememberNavController())
    }
}


