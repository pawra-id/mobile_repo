package id.pawra.ui.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
) {

    viewModel.getSession()
    val userInfo by viewModel.sessionState.collectAsState()

    Column(
        modifier = modifier
            .padding(horizontal = 45.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileImage()

        Text(
            text = userInfo.name.toString(),
            modifier = Modifier.fillMaxWidth(),
            color = DarkGreen,
            fontFamily = Poppins,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = userInfo.email.toString(),
            modifier = Modifier.padding(top = 7.dp),
            color = Gray,
            fontFamily = Poppins,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = userInfo.summary.toString(),
            modifier = Modifier.padding(top = 22.dp),
            color = Black,
            fontFamily = Poppins,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )

    }
}

@Composable
@Preview(showBackground = true)
fun ProfilePagePreview() {
    PawraTheme {
        ProfilePage(viewModel = viewModel(
            factory = ViewModelFactory(LocalContext.current)
        ))
    }
} 