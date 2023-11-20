package id.pawra

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.onboarding.Onboarding
import id.pawra.ui.navigation.AddButton
import id.pawra.ui.navigation.BottomNavigation
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.SignInScreen
import id.pawra.ui.screen.auth.SignUpScreen
import id.pawra.ui.screen.explore.ExploreScreen
import id.pawra.ui.screen.home.HomeScreen
import id.pawra.ui.screen.pet.PetScreen
import id.pawra.ui.screen.profile.ProfileEditScreen
import id.pawra.ui.screen.profile.ProfileScreen
import id.pawra.ui.screen.splashscreen.SplashScreen
import id.pawra.ui.theme.PawraTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Pawra(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController
            )
        }
        composable(Screen.OnBoarding.route) {
            Onboarding(
                navController = navController
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                navController = navController
            )
        }
        composable(Screen.SignIn.route) {
           SignInScreen(
               navController = navController
           )
        }
        composable(Screen.Home.route) {
            HomeNav(navController = navController)
        }
        composable(Screen.EditProfile.route) {
            ProfileEditScreen(
                navController = navController
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNav(
    modifier: Modifier = Modifier,
    navHomeController: NavHostController = rememberNavController(),
    navController: NavHostController
){
    val navBackStackEntry by navHomeController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigation(navHomeController = navHomeController)
        },
        floatingActionButton = {
            AddButton(navHomeController = navHomeController)
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navHomeController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navHomeController = navHomeController)
            }

            composable(Screen.Pet.route) {
                PetScreen(navHomeController = navHomeController)
            }

            composable(Screen.Explore.route) {
                ExploreScreen(navHomeController = navHomeController)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navHomeController = navHomeController,
                    navController = navController
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PawraAppPreview() {
    PawraTheme {
        Pawra()
    }
}