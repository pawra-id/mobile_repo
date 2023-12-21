package id.pawra.ui.screen.pet.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.addactivities.AddActivitiesForm
import id.pawra.ui.components.addactivities.AddActivitiesTitle
import id.pawra.ui.components.addactivities.AddActivitiesTopBar
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.PawraTheme

@Composable
fun ActivitiesAddScreen(
    modifier: Modifier = Modifier,
    petId: Int,
    navController: NavController,
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    Column {
        AddActivitiesTopBar(navController = navController)

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            AddActivitiesTitle()
            Spacer(modifier = Modifier.height(15.dp))
            AddActivitiesForm(
                navController = navController,
                petId = petId,
                activitiesViewModel = activitiesViewModel,
                petViewModel = petViewModel,
                showDialog = { showDialog = it }
            )

            activitiesViewModel.addActivityState.collectAsState().value.let { activityState ->
                when (activityState) {
                    is UiState.Loading -> {
                        isLoading = true
                    }
                    is UiState.Success -> {
                        if(showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = true,
                                message = "Success! your dog activity successfully created",
                                setShowDialog = {
                                    showDialog = it
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                    is UiState.Error -> {
                        if(showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = false,
                                message = activityState.errorMessage,
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
fun ActivitiesAddScreenPreview() {
    PawraTheme {
        ActivitiesAddScreen(
            navController = rememberNavController(),
            petId = 0
        )
    }
}