package id.pawra

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.SessionModel
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.PawraTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PawraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: AuthViewModel = viewModel(
                        factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
                    )

                        viewModel.getSession()

                    val sessionState by viewModel.sessionState.collectAsState()
                    val userInfo = (sessionState as UiState.Success<SessionModel>).data
                    if (userInfo.isLogin) {
                        HomeNav(
                            navController = rememberNavController()
                        )
                    } else {
                        Pawra()
                    }
                }
            }
        }
    }
}