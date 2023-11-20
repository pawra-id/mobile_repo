package id.pawra.ui.screen.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.components.profile.ProfileEditForm
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.MobileGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
    ),
    navController: NavController
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Edit Profile",
                    color = (DarkGreen),
                    fontFamily = Poppins,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium

                )
            },
            actions = {
                AppBarProfileEditActions()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = modifier) {
            val isLoading by remember { mutableStateOf(false) }

            if (isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                var showDialog by remember { mutableStateOf(false) }

                ProfileEditForm(viewModel = viewModel,
                    showDialog = { showDialog = it },
                    navController = navController)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AppBarProfileEditActions(){
    Box(
        modifier = Modifier
            .width(90.dp)
    ) {
        CancelAction(onCancel = {
            // Implement the cancel action logic here
        })
    }
}

@Composable
fun CancelAction(onCancel: () -> Unit) {
    IconButton(
        modifier = Modifier
            .width(90.dp),
        onClick = onCancel
    ) {
        Text(
            text = "Cancel",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = (MobileGray)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ProfileEditScreenPreview() {
    PawraTheme {
        ProfileEditScreen(viewModel = viewModel(
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
        ),
            navController = rememberNavController())
    }
}


