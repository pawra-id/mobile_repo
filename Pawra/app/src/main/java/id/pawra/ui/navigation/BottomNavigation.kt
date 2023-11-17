package id.pawra.ui.navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.ui.theme.PawraTheme
import kotlinx.coroutines.delay

@Composable
fun BottomNavigation(
    navHomeController: NavHostController,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        NavigationBar(
            modifier = modifier.fillMaxWidth(),
            containerColor = colorResource(id = R.color.light_green),
        ) {
            val navBackStackEntry by navHomeController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val navigationItems = listOf(
                NavigationItem(
                    title = stringResource(R.string.menu_home),
                    icon = painterResource(id = R.drawable.menu_home),
                    screen = Screen.Home
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_pet),
                    icon = painterResource(id = R.drawable.menu_pet),
                    screen = Screen.Pet
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_explore),
                    icon = painterResource(id = R.drawable.menu_explore),
                    screen = Screen.Explore
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_profile),
                    icon = painterResource(id = R.drawable.menu_profile),
                    screen = Screen.Profile
                ),
            )

            navigationItems.map { item ->
                val selected = currentRoute == item.screen.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navHomeController.navigate(item.screen.route) {
                            popUpTo(navHomeController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true

                            if (currentRoute == Screen.Home.route){

                            }
                        }
                    },
                    icon = {

                        Box(
                            Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .background(
                                    colorResource(id = if (selected) R.color.white else R.color.light_green),
                                    RoundedCornerShape(50)
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                painter = item.icon,
                                contentDescription = item.title,
                                tint = colorResource(id = if (selected) R.color.light_green else R.color.disabled_green)
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(id = R.color.light_green)
                    )
                )
            }
        }
    }
}

sealed class BackPress {
    object Idle : BackPress()
    object InitialTouch : BackPress()
}

@Composable
fun BackPressSample() {
    var showToast by remember { mutableStateOf(false) }

    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val context = LocalContext.current

    if(showToast){
        Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
        showToast= false
    }


    LaunchedEffect(key1 = backPressState) {
        if (backPressState == BackPress.InitialTouch) {
            delay(2000)
            backPressState = BackPress.Idle
        }
    }

    BackHandler(backPressState == BackPress.Idle) {
        backPressState = BackPress.InitialTouch
        showToast = true
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    PawraTheme {
        BottomNavigation(
            navHomeController = rememberNavController()
        )
    }
}