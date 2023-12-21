package id.pawra.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.ui.common.NoRippleTheme
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun PetBottomNavigation(
    navPetController: NavHostController,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        NavigationBar(
            modifier = modifier.fillMaxWidth(),
            containerColor = LightGreen,
        ) {
            val navBackStackEntry by navPetController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val navigationItems = listOf(
                NavigationItem(
                    title = stringResource(R.string.menu_dog_profile),
                    icon = painterResource(id = R.drawable.menu_pet_profile),
                    screen = Screen.PetProfile
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_dog_activities),
                    icon = painterResource(id = R.drawable.menu_pet_activities),
                    screen = Screen.PetActivities
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_dog_mental_health),
                    icon = painterResource(id = R.drawable.menu_pet_mental_health),
                    screen = Screen.PetMentalHealth
                )
            )

            navigationItems.map { item ->
                val selected = currentRoute == item.screen.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navPetController.navigate(item.screen.route) {
                            popUpTo(navPetController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Box(
                            Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .background(
                                    if (selected) White else LightGreen,
                                    RoundedCornerShape(50)
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                painter = item.icon,
                                contentDescription = item.title,
                                tint = if (selected) LightGreen else DisabledGreen
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = LightGreen
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PetBottomNavigationPreview() {
    PawraTheme {
        PetBottomNavigation(
            navPetController = rememberNavController()
        )
    }
}