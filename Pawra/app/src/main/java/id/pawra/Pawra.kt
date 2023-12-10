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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.pawra.ui.components.onboarding.Onboarding
import id.pawra.ui.navigation.AddButton
import id.pawra.ui.navigation.BottomNavigation
import id.pawra.ui.navigation.PetBottomNavigation
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.SignInScreen
import id.pawra.ui.screen.auth.SignUpScreen
import id.pawra.ui.screen.explore.BlogScreen
import id.pawra.ui.screen.explore.ExploreScreen
import id.pawra.ui.screen.home.HomeScreen
import id.pawra.ui.screen.pet.MentalHealthResultScreen
import id.pawra.ui.screen.pet.activities.ActivitiesAddScreen
import id.pawra.ui.screen.pet.activities.ActivitiesPrevScreen
import id.pawra.ui.screen.pet.activities.PetActivitiesScreen
import id.pawra.ui.screen.pet.profile.PetAddScreen
import id.pawra.ui.screen.pet.mentalhealth.PetMentalHealthScreen
import id.pawra.ui.screen.pet.profile.PetProfileScreen
import id.pawra.ui.screen.pet.PetScreen
import id.pawra.ui.screen.pet.profile.PetUpdateScreen
import id.pawra.ui.screen.profile.ProfileEditScreen
import id.pawra.ui.screen.profile.ProfileScreen
import id.pawra.ui.screen.splashscreen.SplashScreen
import id.pawra.ui.screen.vet.MapAddressScreen
import id.pawra.ui.screen.vet.VetProfileScreen
import id.pawra.ui.screen.vet.VetScreen
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

        composable(
            Screen.PetProfile.route,
            listOf(navArgument("petId") { type = NavType.IntType })
        ) {
            val petId = it.arguments?.getInt("petId") ?: 0
            PetNav(
                navController = navController,
                petId = petId
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController
            )
        }

        composable(Screen.EditProfile.route) {
            ProfileEditScreen(
                navController = navController
            )
        }

        composable(Screen.Vets.route) {
            VetScreen(
                navController = navController
            )
        }

        composable(Screen.MapAddress.route) {
            MapAddressScreen(
                navController = navController
            )
        }

        composable(Screen.PetAdd.route) {
            PetAddScreen(
                navController = navController
            )
        }

        composable(Screen.PetUpdate.route) {
            PetUpdateScreen(
                navController = navController
            )
        }

        composable(
            Screen.PetActivitiesAdd.route,
            listOf(navArgument("petId") {
                nullable = true
            })
        ) {
            val petId = it.arguments?.getString("petId")?.toInt() ?: 0
            ActivitiesAddScreen(
                navController = navController,
                petId = petId
            )
        }

        composable(
            Screen.PetActivitiesPrev.route,
            listOf(navArgument("activityId") { type = NavType.IntType })
        ) {
            val activityId = it.arguments?.getInt("activityId") ?: 0
            ActivitiesPrevScreen(
                navController = navController,
                activityId = activityId
            )
        }

        composable(Screen.VetProfile.route) {
            VetProfileScreen(
                navController = navController
            )
        }

        composable(Screen.PetMentalHealthResult.route) {
            MentalHealthResultScreen(
                navController = navController
            )
        }

        composable(Screen.BlogDetail.route) {
            BlogScreen(
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
            AddButton(
                navController = navController
            )
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navHomeController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navHomeController = navHomeController,
                    navController = navController
                )
            }

            composable(Screen.Pet.route) {
                PetScreen(navHomeController = navHomeController,
                    navController = navController)
            }

            composable(Screen.Explore.route) {
                ExploreScreen(navHomeController = navHomeController)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetNav(
    modifier: Modifier = Modifier,
    navPetController: NavHostController = rememberNavController(),
    navController: NavHostController,
    petId: Int
){
    val navBackStackEntry by navPetController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            PetBottomNavigation(navPetController = navPetController)
        },

        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navPetController,
            startDestination = Screen.PetProfile.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(Screen.PetProfile.route) {
                PetProfileScreen(
                    navController = navController,
                    petId = petId,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            composable(Screen.PetActivities.route) {
                PetActivitiesScreen(
                    navController = navController,
                    petId = petId
                )
            }

            composable(Screen.PetMentalHealth.route) {
                PetMentalHealthScreen(navController = navController)
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