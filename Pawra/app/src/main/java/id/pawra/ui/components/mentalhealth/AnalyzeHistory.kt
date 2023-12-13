package id.pawra.ui.components.mentalhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.data.ViewModelFactory
import id.pawra.data.remote.response.ShareAnalysisResponse
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.mentalhealth.AnalysisViewModel
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red
import id.pawra.ui.theme.White
import id.pawra.utils.DateConverter
import java.text.NumberFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalyzeHistory(
    navController: NavController,
    analysisViewModel: AnalysisViewModel,
    petViewModel: PetViewModel,
    petId: Int,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    analysisViewModel.getSpecificAnalysis(petId, "")

    LaunchedEffect(Unit) {
        petViewModel.getDetailDog(petId)
    }

    var dogName by remember { mutableStateOf("") }
    var dogGender by remember { mutableStateOf("") }
    var dogImage by remember { mutableStateOf("") }

    petViewModel.petDetailState.collectAsState().value.let { petDetail ->
        when(petDetail) {
            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    dogName = petDetail.data.name ?: ""
                    dogGender = petDetail.data.gender ?: ""
                    dogImage = petDetail.data.image ?: ""
                }
            }

            else -> {}
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
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 22.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Analyze History",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                modifier = modifier.weight(1f)
            )
        }

        analysisViewModel.analysisState.collectAsState().value.let { analysisState ->
            when (analysisState) {
                is UiState.Loading -> {
                    isLoading = true
                }
                is UiState.Success -> {
                    isLoading = false
                    LazyColumn(
                        modifier = modifier.padding(horizontal = 22.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (analysisState.data.isNotEmpty()){
                            items(analysisState.data, key = { it.id }) { data ->
                                Row(
                                    modifier = modifier
                                        .height(120.dp)
                                        .clip(shape = RoundedCornerShape(15.dp))
                                        .background(White)
                                        .border(1.dp, DarkGreen, RoundedCornerShape(15.dp))
                                        .clickable {
                                            navController.navigate(
                                                Screen.PetMentalHealthResult.createRoute(
                                                    data.id
                                                )
                                            )
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 15.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = modifier
                                    ) {
                                        AsyncImage(
                                            model = dogImage,
                                            contentDescription = "",
                                            modifier = modifier
                                                .size(90.dp)
                                                .clip(RoundedCornerShape(15.dp))
                                                .border(1.dp, LightGray, RoundedCornerShape(15.dp)),
                                            contentScale = ContentScale.Crop
                                        )

                                        Box(
                                            modifier = modifier
                                                .padding(5.dp)
                                                .align(Alignment.BottomEnd)
                                        ) {
                                            Text(
                                                text = dogGender,
                                                fontSize = 11.sp,
                                                color = if (dogGender == "male") DarkBlue else DarkPink,
                                                modifier = modifier
                                                    .clip(shape = RoundedCornerShape(15.dp))
                                                    .background(
                                                        color = if (dogGender == "male") DisabledBlue else DisabledPink
                                                    )
                                                    .padding(vertical = 2.dp, horizontal = 10.dp)
                                            )
                                        }

                                    }

                                    Box(
                                        modifier = modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            Icons.Filled.Share,
                                            "Share Analyze",
                                            modifier
                                                .align(Alignment.TopEnd)
                                                .clip(CircleShape)
                                                .size(24.dp)
                                                .clickable {
                                                    if (data.isShared == true) {
                                                        analysisViewModel.unshareAnalysis(data.id)
                                                    } else {
                                                        analysisViewModel.shareAnalysis(data.id)
                                                    }
                                                    showDialog = true
                                                },
                                            (if (data.isShared == true) DarkGreen else Red)
                                        )

                                        Column(
                                            modifier = modifier.padding(start = 10.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = dogName,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Black
                                            )

                                            Text(
                                                text = DateConverter.convertStringToDate(data.createdAt ?: ""),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = LightGray
                                            )

                                            Text(
                                                text = data.description ?: "",
                                                fontSize = 13.sp,
                                                color = Black,
                                                lineHeight = 18.sp,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = modifier.weight(1f)
                                            )

                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(10.dp),

                                                ) {
                                                Text(
                                                    text = NumberFormat.getPercentInstance().format(data.prediction?.toFloat()),
                                                    fontSize = 10.sp,
                                                    color = DarkGreen,
                                                    modifier = modifier
                                                        .clip(shape = RoundedCornerShape(15.dp))
                                                        .background(color = DisabledGreen)
                                                        .padding(
                                                            vertical = 2.dp,
                                                            horizontal = 10.dp
                                                        ),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            item {
                                EmptyAnalyzeHistory()
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun AnalyzeHistoryPreview() {
    PawraTheme {
        AnalyzeHistory(
            navController = rememberNavController(),
            petId = 0,
            analysisViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            petViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}