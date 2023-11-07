package id.pawra.ui.components.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import id.pawra.R
import id.pawra.ui.screen.onboarding.OnBoardingPager
import id.pawra.ui.screen.onboarding.rememberPagerState
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(ExperimentalPagerApi::class, DelicateCoroutinesApi::class)
@Composable
fun Onboarding(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {

        val items = ArrayList<OnBoardingData>()

        items.add(
            OnBoardingData(
                R.drawable.onboarding1,
                stringResource(R.string.onboarding_title),
                stringResource(R.string.onboarding1_subtitle),
                stringResource(R.string.onboarding1_desc),
                backgroundColor = (LightGreen),
                mainColor = (DarkGreen),
            )
        )

        items.add(
            OnBoardingData(
                R.drawable.onboarding2,
                stringResource(R.string.onboarding_title),
                stringResource(R.string.onboarding2_subtitle),
                stringResource(R.string.onboarding2_desc),
                backgroundColor = (LightGreen),
                mainColor = (DarkGreen),
            )
        )

        items.add(
            OnBoardingData(
                R.drawable.onboarding3,
                stringResource(R.string.onboarding_title),
                stringResource(R.string.onboarding3_subtitle),
                stringResource(R.string.onboarding3_desc),
                backgroundColor = (LightGreen),
                mainColor = (DarkGreen),
            )
        )


        val pagerState = rememberPagerState(
            pageCount = items.size,
            initialOffscreenLimit = 2,
            infiniteLoop = false,
            initialPage = 0,
        )


        OnBoardingPager(
            item = items, pagerState = pagerState, navController
        )
    }
}

@Composable
fun PagerIndicator(currentPage: Int, items: List<OnBoardingData>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(items.size) {
            Indicator(isSelected = it == currentPage, color = items[it].mainColor)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean, color: Color) {
    val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp, label = "animasi on boarding",
    )

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
            )
    )
}