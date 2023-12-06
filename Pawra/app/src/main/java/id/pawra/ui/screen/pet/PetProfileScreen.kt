package id.pawra.ui.screen.pet

import PetProfile
import android.os.Build
import androidx.annotation.RequiresApi
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
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.components.petprofile.PetProfileTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petId: Int,
    navigateBack: () -> Unit,
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    petViewModel.getDetailDog(petId)
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
        PetProfileTopBar(navController = navController)

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            petViewModel.petDetailState.collectAsState().value.let { petDetailState ->
                when (petDetailState) {
                    is UiState.Success -> {
                        PetProfile(
                            pet = petDetailState.data
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
                                message = petDetailState.errorMessage,
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun PetProfileScreenPreview() {
    PawraTheme {
        PetProfileScreen(
            navController = rememberNavController(),
            petId = 0,
            navigateBack = {}
        )
    }
}