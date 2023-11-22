package id.pawra.ui.components.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun ProfileButton(
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
    ),
    navController: NavController
){

    Row(
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 30.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.EditProfile.route)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .border(2.dp, DarkGreen, shape = RoundedCornerShape(10.dp)),
        ) {
            Text(
                text = stringResource(R.string.edit),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 7.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))

        val buttonColors = ButtonDefaults.buttonColors(White)

        Button(
            onClick = {
                viewModel.logout()
                navController.navigate(Screen.SignIn.route){
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .border(2.dp, DarkGreen, shape = RoundedCornerShape(10.dp)),
            colors = buttonColors
        ) {
            Text(
                text = stringResource(R.string.logout),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = (DarkGreen),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 7.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileButtonPreview() {
    PawraTheme {
        ProfileButton(navController = rememberNavController())
    }
}