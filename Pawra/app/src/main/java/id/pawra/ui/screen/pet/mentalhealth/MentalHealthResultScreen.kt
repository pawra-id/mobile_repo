package id.pawra.ui.screen.pet.mentalhealth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import id.pawra.ui.components.general.BottomSheet
import id.pawra.ui.components.mentalhealth.MentalHealthAction
import id.pawra.ui.components.mentalhealth.MentalHealthResult
import id.pawra.ui.components.mentalhealth.MentalHealthResultGraph
import id.pawra.ui.components.mentalhealth.MentalHealthResultTitle
import id.pawra.ui.components.mentalhealth.MentalHealthResultTopBar
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun MentalHealthResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    analysisId: Int,
    analysisViewModel: AnalysisViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    isShared: Boolean
) {
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (isShared) {
        analysisViewModel.getDetailSharedAnalysis(analysisId)
    } else {
        analysisViewModel.getDetailAnalysis(analysisId)
    }

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

    analysisViewModel.analysisDetailState.collectAsState().value.let { detailAnalysisState ->
        when (detailAnalysisState) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                BottomSheet(
                    expanded = false,
                    content = {
                        Column {
                            isLoading = false

                            MentalHealthResultTopBar(
                                navController = navController,
                                analysisViewModel = analysisViewModel,
                                isShared = detailAnalysisState.data.isShared ?: false,
                                analysisId = detailAnalysisState.data.id,
                                isSharedScreen = isShared,
                                setShowDialog = {
                                    showDialog = it
                                }
                            )

                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(start = 22.dp, end = 22.dp, bottom = 56.dp)
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                            ) {

                                MentalHealthResultTitle(
                                    petName = detailAnalysisState.data.dog?.name ?: ""
                                )
                                val percentage =
                                    detailAnalysisState.data.prediction?.toFloat()?.times(100) ?: 0f
                                MentalHealthResultGraph(
                                    percentage
                                )
                                MentalHealthResult(
                                    percentage = percentage,
                                    descriptionResult = detailAnalysisState.data.description ?: ""
                                )

                                Text(
                                    text = "swipe up to see recommended actions",
                                    modifier = Modifier.padding(top = 25.dp, bottom = 15.dp),
                                    color = (Gray),
                                    fontFamily = Poppins,
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    }
                ) {
                    MentalHealthAction(
                        listAction = detailAnalysisState.data.actions ?: listOf()
                    )
                }

            }

            is UiState.Error -> {
                if (showDialog) {
                    isLoading = false
                    ResultDialog(
                        success = false,
                        message = detailAnalysisState.errorMessage,
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

@Composable
@Preview(showBackground = true)
fun MentalHealthResultScreenPreview() {
    PawraTheme {
        MentalHealthResultScreen(
            navController = rememberNavController(),
            analysisId = 0,
            isShared = false
        )
    }
}