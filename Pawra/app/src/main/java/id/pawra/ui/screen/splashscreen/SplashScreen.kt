package id.pawra.ui.screen.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import kotlinx.coroutines.delay
import id.pawra.ui.navigation.Screen

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0.5f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(Screen.OnBoarding.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(206.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(206.dp)
                    .scale(scale.value)
            )
        }

        Spacer(modifier = Modifier.height(160.dp))

        Text(
            text = "created by",
            color = Color.White,
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Image(
            painter = painterResource(id = R.drawable.ic_logo2),
            contentDescription = "Logo2",
            modifier = Modifier.size(105.dp, 59.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    PawraTheme {
        SplashScreen(rememberNavController())
    }
}