package id.pawra.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import id.pawra.ui.components.signup.SignUpFooter
import id.pawra.ui.components.signup.SignUpForm
import id.pawra.ui.components.signup.SignUpHeader

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SignUpHeader()
        SignUpForm()
        SignUpFooter(
            navController = navController
        )
    }
}