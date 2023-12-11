package id.pawra.ui.screen.pet.activities

import android.os.Build
import androidx.annotation.RequiresApi
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
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.ui.common.UiState
import id.pawra.ui.components.addactivities.ActivitiesPrevInfo
import id.pawra.ui.components.addactivities.ActivitiesPrevTitle
import id.pawra.ui.components.addactivities.ActivitiesPrevTopBar
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.PawraTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivitiesPrevScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    activityId: Int,
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    activitiesViewModel.getDetailActivity(activityId)
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
        ActivitiesPrevTopBar(navController = rememberNavController())

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            ActivitiesPrevTitle()

            Spacer(modifier = Modifier.height(15.dp))
            activitiesViewModel.activityDetailState.collectAsState().value.let { activityDetailState ->
                when (activityDetailState) {
                    is UiState.Success -> {
                        isLoading = false
                        ActivitiesPrevInfo(
                            activity = activityDetailState.data,
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
                                message = activityDetailState.errorMessage,
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
fun ActivitiesPrevScreenPreview() {
    PawraTheme {
        ActivitiesPrevScreen(
            activityId = 0,
            navController = rememberNavController()
        )
    }
}