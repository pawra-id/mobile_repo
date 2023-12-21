package id.pawra.ui.components.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
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
import androidx.compose.ui.graphics.Color
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
import id.pawra.data.remote.response.AnalysisResponseItem
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.mentalhealth.AnalysisViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DarkYellow
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.DisabledRed
import id.pawra.ui.theme.DisabledYellow
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red
import id.pawra.ui.theme.White
import id.pawra.utils.DateConverter
import java.text.NumberFormat

@Composable
fun SharedMental(
    modifier: Modifier = Modifier,
    navController: NavController,
    analysisViewModel: AnalysisViewModel
) {

    val query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        analysisViewModel.getSharedAnalysis("")
    }

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

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchBar(
            query = query,
            onQueryChange = analysisViewModel::getSharedAnalysis,
            onSearch = {
            },
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Gray
                )
            },
            placeholder = {
                Text(
                    "Search",
                    color = Gray
                )
            },
            modifier = modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {}

        analysisViewModel.sharedAnalysisState.collectAsState().value.let { sharedAnalysis ->
            when (sharedAnalysis) {
                is UiState.Loading -> {
                    isLoading = true
                }

                is UiState.Success -> {
                    isLoading = false

                    LazyColumn(
                        modifier = modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (sharedAnalysis.data.isNotEmpty()) {
                            items(sharedAnalysis.data, key = { it.id }){ data ->
                                val percentage = data.prediction?.toFloat()?.times(100) ?: 0f
                                var color = Red
                                var disableColor = DisabledRed

                                if (percentage.toInt() < 25) {
                                    color = DarkGreen
                                    disableColor = DisabledGreen
                                } else if(percentage.toInt() < 50) {
                                    color = DarkBlue
                                    disableColor = DisabledBlue
                                } else if(percentage.toInt() < 75) {
                                    color = DarkYellow
                                    disableColor = DisabledYellow
                                }

                                SharedMentalItem(
                                    navController = navController,
                                    analysisViewModel = analysisViewModel,
                                    analysisData = data,
                                    dogImage =  data.dog?.image ?: "",
                                    dogGender = data.dog?.gender ?: "",
                                    dogName = data.dog?.name ?: "",
                                    color = color,
                                    disableColor = disableColor,
                                    showDialog = {
                                        showDialog = it
                                    }
                                )
                            }
                        }
                        else {
                            item {
                                EmptyBlogs(
                                    modifier.fillMaxHeight(1f)
                                )
                            }
                        }
                    }

                }

                is UiState.Error -> {
                    if (showDialog) {
                        isLoading = false
                        ResultDialog(
                            success = false,
                            message = sharedAnalysis.errorMessage,
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
fun SharedMentalItem(
    navController: NavController,
    analysisViewModel: AnalysisViewModel,
    analysisData: AnalysisResponseItem,
    dogImage: String,
    dogGender: String,
    dogName: String,
    color: Color,
    disableColor: Color,
    showDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .height(120.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(White)
            .clickable {
                navController.navigate(
                    Screen.PetMentalHealthSharedResult.createRoute(
                        analysisData.id
                    )
                )
            }
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = modifier
        ) {
            AsyncImage(
                model = dogImage,
                contentDescription = "Dog Image",
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
                Icon(
                    if (dogGender == "male") Icons.Filled.Male else Icons.Filled.Female,
                    "Sex Icon",
                    modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(
                            color = if (dogGender == "male") DisabledBlue else DisabledPink
                        )
                        .padding(vertical = 2.dp, horizontal = 10.dp)
                        .size(18.dp),
                    (if (dogGender == "male") DarkBlue else DarkPink)
                )
            }

        }

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
                text = DateConverter.convertStringToDate(analysisData.createdAt ?: ""),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = LightGray
            )

            Text(
                text = analysisData.description ?: "",
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
                    text = "${NumberFormat.getPercentInstance().format(analysisData.prediction?.toFloat())} has depression",
                    fontSize = 10.sp,
                    color = color,
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(15.dp))
                        .background(color = disableColor)
                        .padding(
                            vertical = 2.dp,
                            horizontal = 10.dp
                        ),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SharedMentalPreview() {
    PawraTheme {
        SharedMental(
            navController = rememberNavController(),
            analysisViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}