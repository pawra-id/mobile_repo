package id.pawra.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash_screen")
    data object OnBoarding : Screen("onboarding")
    data object SignUp : Screen("signup")
    data object SignIn : Screen("signin")
    data object Home : Screen("home")
    data object Pet : Screen("pet")
    data object Explore : Screen("explore")
    data object Profile : Screen("profile")
}