package id.pawra.ui.components.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.auth.SignInFormEvent
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    showDialog: (Boolean) -> Unit,
    navController: NavController
) {
    val state = viewModel.stateSignIn

    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.username),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())
        OutlinedTextField(
            value = state.name,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { viewModel.onEventSignIn(SignInFormEvent.NameChanged(it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = Gray,
                fontFamily = Poppins
            ),
            supportingText = {
                if (state.nameError != null) {
                    Text(
                        text = state.nameError,
                        color = Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            },
            isError = state.nameError != null
        )
        Text(
            text = stringResource(R.string.password),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        var showPassword by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = state.password,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }, trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_24),
                            stringResource(
                                R.string.show_password
                            )
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_off_24),
                            stringResource(
                                R.string.hide_password
                            )
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onEventSignIn(SignInFormEvent.PasswordChanged(it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = Gray,
                fontFamily = Poppins
            ),
            supportingText = {
                if (state.passwordError != null) {
                    Text(
                        text = state.passwordError,
                        color = Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            },
            isError = state.passwordError != null
        )

//        ClickableText(
//            text = AnnotatedString(stringResource(R.string.forgot_password)),
//            style = TextStyle(
//                color = DarkGreen,
//                fontWeight = FontWeight.SemiBold,
//                fontFamily = Poppins,
//                textDecoration = TextDecoration.Underline
//            ),
//            onClick = {
//                TODO: set route to forgot password screen
//                navController.navigate("")
//                Toast.makeText(context, "not developed yet", Toast.LENGTH_SHORT).show()
//            },
//            modifier = modifier.align(Alignment.End)
//        )

        Button(
            onClick = {
                viewModel.onEventSignIn(SignInFormEvent.Submit)
                showDialog(viewModel.showDialog)},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.sign_in), fontSize = 14.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignInFormPreview() {
    PawraTheme {
        SignInForm(viewModel = viewModel(
            factory = ViewModelFactory(LocalContext.current)
        ),
            showDialog = {  }
            ,navController = rememberNavController())
    }
}