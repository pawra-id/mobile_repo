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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import id.pawra.data.remote.response.AnalysisResponseItem
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.mentalhealth.AnalysisViewModel
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
    analysisData: AnalysisResponseItem,
    dogImage: String,
    dogGender: String,
    dogName: String,
    showDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
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
                        if (analysisData.isShared == true) {
                            analysisViewModel.unshareAnalysis(analysisData.id)
                        } else {
                            analysisViewModel.shareAnalysis(analysisData.id)
                        }
                        showDialog(true)
                    },
                (if (analysisData.isShared == true) DarkGreen else Red)
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun AnalyzeHistoryPreview() {
    PawraTheme {
        AnalyzeHistory(
            navController = rememberNavController(),
            analysisViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            analysisData = AnalysisResponseItem(id=0),
            "",
            "",
            "",
            {}
        )
    }
}