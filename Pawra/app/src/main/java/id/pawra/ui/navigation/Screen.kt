package id.pawra.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash_screen")
    data object OnBoarding : Screen("onboarding")
    data object SignUp : Screen("signup")
    data object SignIn : Screen("signin")

    // Main Navigation
    data object Home : Screen("home")
    data object Pet : Screen("pet")
    data object Explore : Screen("explore")
    data object Profile : Screen("profile")

    // Profile Navigation
    data object EditProfile : Screen("edit_profile")

    // Pet Navigation
    data object PetProfile : Screen("pet_profile")
    data object PetActivities : Screen("pet_activities")
    data object PetMentalHealth : Screen("pet_mental_health")
}