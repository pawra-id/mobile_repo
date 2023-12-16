@file:Suppress("DEPRECATION")

package id.pawra.ui.screen.onboarding

import android.preference.PreferenceManager
import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import id.pawra.R
import id.pawra.ui.components.onboarding.OnBoardingData
import id.pawra.ui.components.onboarding.Onboarding
import id.pawra.ui.components.onboarding.PagerIndicator
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.BottomCardShape
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalPagerApi
@Composable
fun OnBoardingPager(
    item: List<OnBoardingData>,
    pagerState: PagerState,
    navController: NavController
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen),
        contentAlignment = Alignment.TopCenter,
    ) {

        Image(
            painter = painterResource(id = R.drawable.background_onboarding),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalPager(state = pagerState) { page ->
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = item[page].image),
                            contentDescription = item[page].title,
                            modifier = Modifier
                                .size(206.dp, 260.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    ),
                    shape = BottomCardShape.large,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(500.dp, 270.dp)
                            .background(Color.White)
                    ) {
                        PagerIndicator(items = item, currentPage = pagerState.currentPage)
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = item[pagerState.currentPage].title,
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = item[pagerState.currentPage].mainColor,
                            fontFamily = Poppins,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = item[pagerState.currentPage].subtitle,
                            modifier = Modifier.padding(
                                top = 20.dp,
                                start = 30.dp,
                                end = 30.dp
                            ),
                            color = (Gray),
                            fontFamily = Poppins,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = item[pagerState.currentPage].desc,
                            modifier = Modifier.padding(top = 5.dp, start = 30.dp, end = 30.dp),
                            color = (Gray),
                            fontFamily = Poppins,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(bottom = 200.dp)
                    ){
                        Button(
                            enabled = pagerState.currentPage == 2,
                            onClick = {
                                PreferenceManager.getDefaultSharedPreferences(context).edit()
                                    .putBoolean("IS_FIRST_LAUNCHED", false)
                                    .apply()
                                navController.navigate(Screen.SignUp.route){
                                    popUpTo(Screen.OnBoarding.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp, start = 30.dp, end = 30.dp)
                                .height(50.dp)
                        ) {
                            Text(
                                text = "Next",
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun rememberPagerState(
    @androidx.annotation.IntRange(from = 0) pageCount: Int,
    @androidx.annotation.IntRange(from = 0) initialPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
    @androidx.annotation.IntRange(from = 1) initialOffscreenLimit: Int = 1,
    infiniteLoop: Boolean = false
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        pageCount = pageCount,
        currentPage = initialPage,
        currentPageOffset = initialPageOffset,
        offscreenLimit = initialOffscreenLimit,
        infiniteLoop = infiniteLoop
    )
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    PawraTheme {
        Onboarding(rememberNavController())
    }
}