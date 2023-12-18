package id.pawra.ui.screen.vet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.VetData
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.components.petprofile.PetProfile
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
    vetViewModel: VetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    vetId: Int,
    navController: NavController
) {

    vetViewModel.getDetailVet(vetId)
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    if (isLoading) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    Column {
        VetProfileTopBar(
            navController = navController
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            vetViewModel.vetDetailState.collectAsState().value.let { vetDetailState ->
                when (vetDetailState) {
                    is UiState.Success -> {
                        isLoading = false
                        VetProfile(
                            vet = vetDetailState.data,
                            navController = navController,
                            vetViewModel = vetViewModel
                        )
                    }
                    is UiState.Loading -> {
                        isLoading = true
                    }

                    is UiState.Error -> {
                        if (showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = false,
                                message = vetDetailState.errorMessage,
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

@Composable
@Preview(showBackground = true)
fun VetProfileScreenPreview() {
    PawraTheme {
        VetProfileScreen(
            navController = rememberNavController(),
            vetId = 0,
            vetViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}