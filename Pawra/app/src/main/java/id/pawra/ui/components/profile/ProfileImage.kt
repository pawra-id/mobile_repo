package id.pawra.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.SessionModel
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.PawraTheme

@Composable
fun ProfileImage (
    viewModel: AuthViewModel
) {

    LaunchedEffect(true) {
        viewModel.getSession()
    }

    val sessionState by viewModel.sessionState.collectAsState()

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            Image(
                painterResource(id = R.drawable.background_profileimage),
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp),
                contentDescription = "Background Profile Image",
            )
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(18.dp)
                    .size(130.dp)
            ) {
                when (sessionState) {
                    is UiState.Success -> {
                        val userInfo = (sessionState as UiState.Success<SessionModel>).data
                        Image(
                            painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = null,
                            modifier = Modifier
                                .wrapContentSize()
                                .size(130.dp)
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
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileImagePreview() {
    PawraTheme {
        ProfileImage(viewModel = viewModel(
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
        ))
    }
}