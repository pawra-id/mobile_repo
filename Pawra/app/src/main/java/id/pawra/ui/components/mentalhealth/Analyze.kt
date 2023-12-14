package id.pawra.ui.components.mentalhealth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.mentalhealth.AnalysisViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun Analyze(
    modifier: Modifier = Modifier,
    navController: NavController,
    analysisViewModel: AnalysisViewModel,
    petId: Int
) {
    var showDialog by remember { mutableStateOf(false) }
    var isLoadingAnalyze by remember { mutableStateOf(false) }

    if (isLoadingAnalyze) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    if(showDialog) {
        AnalyzePopUpForm(
            analysisViewModel = analysisViewModel,
            setShowDialog = {
                showDialog = it
            },
            petId = petId
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Button(
            modifier = modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                showDialog = true
            }
        ) {
            Text(
                text = "Analyze",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        var isFinished by rememberSaveable { mutableStateOf(false) }
        var hasBeenClick by rememberSaveable { mutableStateOf(false) }

        var newAnalysis by remember { mutableIntStateOf(0) }
        analysisViewModel.addAnalysisState.collectAsState().value.let { addAnalyzeState ->
            when (addAnalyzeState) {
                is UiState.Loading -> {
                    isLoadingAnalyze = true
                }

                is UiState.Success -> {
                    isLoadingAnalyze = false
                    newAnalysis = addAnalyzeState.data.id
                    isFinished = true
                }

                is UiState.Error -> {
                    if (showDialog) {
                        isLoadingAnalyze = false
                        ResultDialog(
                            success = false,
                            message = addAnalyzeState.errorMessage,
                            setShowDialog = {
                                showDialog = it
                            }
                        )
                    }
                }

                else -> {}
            }

        }

        AnimatedVisibility(
            visible = isFinished and !hasBeenClick,
            enter = fadeIn(animationSpec = tween(2000)),
            exit = fadeOut(animationSpec = tween(2000))
        ) {
            Box(
                modifier = modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = DisabledGreen)
                    .clickable {
                        hasBeenClick = true
                        isFinished = false
                        navController.navigate(
                            Screen.PetMentalHealthResult.createRoute(
                                newAnalysis
                            )
                        )
                    },
            ) {
                Column(
                    modifier = modifier
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Analyzing mental health finished",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGreen,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Click this card to see the result",
                        fontSize = 13.sp,
                        color = DarkGreen,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.success_icon),
                        contentDescription = "Success Icon",
                        tint = Color.Unspecified,
                        modifier = modifier
                            .size(80.dp)
                            .padding(top = 15.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AnalyzePreview() {
    PawraTheme {
        Analyze(
            navController = rememberNavController(),
            analysisViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            petId = 0
        )
    }
}