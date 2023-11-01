package id.pawra

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.SignInScreen
import id.pawra.ui.screen.auth.SignUpScreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun Pawra(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignUp.route,
        modifier = modifier
    ) {
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
    }

}

@Preview(showBackground = true)
@Composable
fun PawraAppPreview() {
    PawraTheme {
        Pawra()
    }
}