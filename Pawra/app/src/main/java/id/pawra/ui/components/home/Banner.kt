package id.pawra.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.Linear1
import id.pawra.ui.theme.Linear2
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.White

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    navHomeController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val bannerWidth = screenWidth - 44.dp

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState(), true)
            .padding(horizontal = 22.dp, vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = modifier
                .height(130.dp)
                .width(bannerWidth)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = DisabledGreen),
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Check your dog now",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = DarkGreen,
                )
                Text(
                    text = "Don't waste any more time",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = DarkGreen,
                    lineHeight = 15.sp
                )

            }

            Image(
                painter = painterResource(id = R.drawable.dog_banner),
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp)
                    .height(90.dp)
            )

            Button(
                onClick = {
                    navHomeController.navigate(Screen.Pet.route) {
                        popUpTo(navHomeController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .height(30.dp)
                    .width(65.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen
                ),
            ) {
                Text(
                    text = "Go",
                    fontFamily = Poppins,
                    fontSize = 13.sp
                )
            }
        }

        Box(
            modifier = modifier
                .height(130.dp)
                .width(bannerWidth)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Linear1,
                            Linear2
                        )
                    )
                ),
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Find out more about others",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = White,
                )
                Text(
                    text = "Check these shared mental health result",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = White,
                    lineHeight = 15.sp
                )
            }

            Button(
                onClick = {
                    navHomeController.navigate(Screen.Explore.route) {
                        popUpTo(navHomeController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    } },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .height(30.dp)
                    .width(80.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = White
                ),
            ) {
                Text(
                    text = "Explore",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Linear1,
                                Linear2
                            ),
                        )
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BannerPreview() {
    PawraTheme {
        Banner(
            navHomeController = rememberNavController()
        )
    }
}