package id.pawra

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.onboarding.Onboarding
import id.pawra.ui.navigation.AddButton
import id.pawra.ui.navigation.BottomNavigation
import id.pawra.ui.navigation.PetBottomNavigation
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.auth.SignInScreen
import id.pawra.ui.screen.auth.SignUpScreen
import id.pawra.ui.screen.explore.BlogScreen
import id.pawra.ui.screen.explore.ExploreScreen
import id.pawra.ui.screen.home.HomeScreen
import id.pawra.ui.screen.pet.mentalhealth.MentalHealthResultScreen
import id.pawra.ui.screen.pet.activities.ActivitiesAddScreen
import id.pawra.ui.screen.pet.activities.ActivitiesPrevScreen
import id.pawra.ui.screen.pet.activities.PetActivitiesScreen
import id.pawra.ui.screen.pet.profile.PetAddScreen
import id.pawra.ui.screen.pet.mentalhealth.PetMentalHealthScreen
import id.pawra.ui.screen.pet.profile.PetProfileScreen
import id.pawra.ui.screen.pet.PetScreen
import id.pawra.ui.screen.pet.activities.ActivitiesUpdateScreen
import id.pawra.ui.screen.pet.profile.PetUpdateScreen
import id.pawra.ui.screen.profile.ProfileEditScreen
import id.pawra.ui.screen.profile.ProfileScreen
import id.pawra.ui.screen.splashscreen.SplashScreen
import id.pawra.ui.screen.vet.MapAddressScreen
import id.pawra.ui.screen.vet.VetProfileScreen
import id.pawra.ui.screen.vet.VetScreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun Pawra(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )

    viewModel.getSession()
    val sessionState by viewModel.sessionState.collectAsState()

    val initialRoute: String = if (!sessionState.isLaunched) {
        Screen.SplashScreen.route
    } else {
        if (sessionState.isLogin) {
            Screen.Home.route
        } else {
            Screen.SignIn.route
        }
    }

    val destination = if (sessionState.isLogin){
        Screen.Home.route
    } else{
        if (!PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
                .getBoolean("IS_FIRST_LAUNCHED", true)) {
            Screen.SignIn.route
        } else {
            Screen.OnBoarding.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = initialRoute,
        modifier = modifier
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController,
                destination = destination
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
            val activityId = it.arguments?.getInt("activityId") ?: 0
            PetNav(
                navController = navController,
                petId = petId,
                activityId = activityId
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

        composable(
            Screen.PetUpdate.route,
            listOf(navArgument("petId") { type = NavType.IntType })
        ) {
            val petId = it.arguments?.getInt("petId") ?: 0
            PetUpdateScreen(
                navController = navController,
                petId = petId
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
            Screen.PetActivitiesUpdate.route,
            listOf(navArgument("activityId") { type = NavType.IntType })
        ) {
            val activityId = it.arguments?.getInt("activityId") ?: 0
            ActivitiesUpdateScreen(
                navController = navController,
                activityId = activityId
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

        composable(
            Screen.VetProfile.route,
            listOf(navArgument("vetId") { type = NavType.IntType })
        ) {
            val vetId = it.arguments?.getInt("vetId") ?: 0
            VetProfileScreen(
                navController = navController,
                vetId = vetId
            )
        }

        composable(
            Screen.BlogDetail.route,
            listOf(navArgument("blogsId") { type = NavType.IntType })
        ) {
            val blogsId = it.arguments?.getInt("blogsId") ?: 0
            BlogScreen(
                navController = navController,
                blogsId = blogsId
            )
        }

        composable(
            Screen.PetMentalHealthResult.route,
            listOf(navArgument("analysisId") { type = NavType.IntType })
        ) {
            val analysisId = it.arguments?.getInt("analysisId") ?: 0
            MentalHealthResultScreen(
                navController = navController,
                analysisId = analysisId
            )
        }
    }

}

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
                PetScreen(
                    navController = navController
                )
            }

            composable(Screen.Explore.route) {
                ExploreScreen(
                    navHomeController = navHomeController,
                    navController = navController
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun PetNav(
    modifier: Modifier = Modifier,
    navPetController: NavHostController = rememberNavController(),
    navController: NavHostController,
    petId: Int,
    activityId: Int
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
                    petId = petId,
                    activityId = activityId
                )
            }

            composable(Screen.PetMentalHealth.route) {
                PetMentalHealthScreen(
                    navController = navController,
                    petId = petId
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PawraAppPreview() {
    PawraTheme {
        Pawra()
    }
}