package id.pawra.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun BottomNavigation(
    navHomeController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
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
                    }
                },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.title,
                        tint = if (selected) colorResource(id = R.color.light_green) else colorResource(id = R.color.disabled_green)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor= Color.White
                )
            )
        }
    }
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