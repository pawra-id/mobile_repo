package id.pawra.ui.screen.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import id.pawra.ui.components.profile.ProfilePage
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Profile Page",
                    color = (DarkGreen),
                    fontFamily = Poppins,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium

                )
            },
            actions = {
                AppBarProfileActions()
            }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            ProfilePage(viewModel = viewModel(
                factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
            ),
                navController = rememberNavController())

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 30.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.SignIn.route)
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
                        navController.navigate(Screen.EditProfile.route)
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

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun AppBarProfileActions(){
    Box(
        modifier = Modifier
            .padding(15.dp)
    ){
        SettingAction()
    }
}

@Composable
fun SettingAction() {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_settings_24),
        modifier = Modifier
            .width(25.dp),
        contentDescription = "Setting Language",
        tint = (Black)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    PawraTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}