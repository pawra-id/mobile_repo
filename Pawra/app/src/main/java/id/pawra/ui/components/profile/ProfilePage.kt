package id.pawra.ui.components.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.SessionModel
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.*

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController
) {
    LaunchedEffect(true) {
        viewModel.getSession()
    }

    val sessionState by viewModel.sessionState.collectAsState()

    Column(
        modifier = modifier
            .padding(horizontal = 45.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileImage(viewModel = viewModel(
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
        ))

        when (sessionState) {
            is UiState.Success -> {
                val userInfo = (sessionState as UiState.Success<SessionModel>).data
                Text(
                    text = userInfo.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = DarkGreen,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = userInfo.email,
                    modifier = Modifier.padding(top = 7.dp),
                    color = MobileGray,
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    text = userInfo.summary,
                    modifier = Modifier.padding(top = 22.dp),
                    color = Black,
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }

            is UiState.Error -> {

                Text(text = "Error: ${(sessionState as UiState.Error).errorMessage}")
            }

            else -> {
                Text(text = "Loading...")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfilePagePreview() {
    PawraTheme {
        ProfilePage(viewModel = viewModel(
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
        ),
            navController = rememberNavController())
    }
} 