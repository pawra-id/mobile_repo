package id.pawra.ui.components.explore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import id.pawra.data.ViewModelFactory
import id.pawra.data.remote.response.AnalysisResponseItem
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.mentalhealth.AnalysisViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White
import id.pawra.utils.DateConverter
import java.text.NumberFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedMental(
    modifier: Modifier = Modifier,
    analysisViewModel: AnalysisViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    analysisId: Int
) {
    val query by remember { mutableStateOf("") }
    val sharedAnalysisState by analysisViewModel.sharedAnalysisState.collectAsState()

    LaunchedEffect(Unit) {
        analysisViewModel.getSharedAnalysis(analysisId = analysisId)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchBar(
            query = query,
            onQueryChange = {
            },
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

        LazyColumn(
            modifier = modifier.weight(1f),
        ) {
//            items(items = sharedAnalysisState.data?.items ?: emptyList<AnalysisResponseItem>()) { sharedAnalysisItem ->
//                SharedMentalItem(
//                    navController = rememberNavController(),
//                    analysisViewModel = viewModel(
//                        factory = ViewModelFactory(LocalContext.current)
//                    ),
//                    analysisData = AnalysisResponseItem(id=0),
//                    "",
//                    "",
//                    "",
//                    {}
//                )
//            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedMentalItem(
    navController: NavController,
    analysisViewModel: AnalysisViewModel,
    analysisData: AnalysisResponseItem,
    dogImage: String,
    dogGender: String,
    dogName: String,
    showDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){

    Row(
        modifier = modifier
            .height(120.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(White)
            .border(1.dp, DarkGreen, RoundedCornerShape(15.dp))
            .clickable {
                navController.navigate(
                    Screen.PetMentalHealthResult.createRoute(
                        analysisData.id
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
                    text = NumberFormat.getPercentInstance().format(analysisData.prediction?.toFloat()),
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun SharedMentalPreview() {
    PawraTheme {
        SharedMental(analysisViewModel = viewModel(
            factory = ViewModelFactory(LocalContext.current)),
            analysisId = 0
            )
    }
}