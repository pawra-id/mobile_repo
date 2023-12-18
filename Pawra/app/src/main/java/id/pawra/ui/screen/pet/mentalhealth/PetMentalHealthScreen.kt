package id.pawra.ui.screen.pet.mentalhealth

import  android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.components.mentalhealth.Analyze
import id.pawra.ui.components.mentalhealth.AnalyzeHistory
import id.pawra.ui.components.mentalhealth.EmptyAnalyzeHistory
import id.pawra.ui.components.mentalhealth.MentalHealthTopBar
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetMentalHealthScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petId: Int,
    analysisViewModel: AnalysisViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    analysisViewModel.getSpecificAnalysis(petId, "")

    if (isLoading) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    if (showDialog) {
        analysisViewModel.shareState.collectAsState().value.let { data ->
            ResultDialog(
                success = !data.error,
                message = data.message,
                setShowDialog = {
                    showDialog = it
                }
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MentalHealthTopBar()

        analysisViewModel.analysisState.collectAsState().value.let { analysisState ->
            when (analysisState) {
                is UiState.Loading -> {
                    isLoading = true
                }

                is UiState.Success -> {
                    isLoading = false

                    LazyColumn(
                        modifier = modifier
                            .padding(horizontal = 22.dp, vertical = 20.dp)
                            .fillMaxHeight()
                    ) {
                        item { Analyze(
                            navController = navController,
                            analysisViewModel = analysisViewModel,
                            petId = petId
                        ) }

                        item { Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Analyze History",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Black,
                                modifier = modifier.weight(1f)
                            )
                        } }

                        if (analysisState.data.isNotEmpty()) {
                            items(analysisState.data, key = { it.id }) { data ->
                                Column(
                                    modifier = modifier.padding(vertical = 5.dp)
                                ) {
                                    AnalyzeHistory(
                                        navController = navController,
                                        analysisViewModel = analysisViewModel,
                                        analysisData = data,
                                        dogImage =  data.dog?.image ?: "",
                                        dogGender = data.dog?.gender ?: "",
                                        dogName = data.dog?.name ?: "",
                                        showDialog = {
                                            showDialog = it
                                        }
                                    )
                                }
                            }
                        } else {
                            item {
                                EmptyAnalyzeHistory(
                                    modifier.fillMaxHeight(1f)
                                )
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    if(showDialog) {
                        isLoading = false
                        ResultDialog(
                            success = false,
                            message = analysisState.errorMessage,
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

@Composable
@Preview(showBackground = true)
fun PetMentalHealthScreenPreview() {
    PawraTheme {
        PetMentalHealthScreen(
            navController = rememberNavController(),
            petId = 0
        )
    }
}