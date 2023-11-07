package id.pawra.ui.components.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    showDialog: (Boolean) -> Unit,
    navController: NavController
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var email by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }

        Text(
            text = stringResource(R.string.email),
            color = colorResource(id = R.color.gray),
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())
        OutlinedTextField(
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email = it },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = colorResource(id = R.color.gray),
                fontFamily = Poppins
            )
        )
        Text(
            text = stringResource(R.string.password),
            color = colorResource(id = R.color.gray),
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        var showPassword by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
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
            onValueChange = { password = it },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = colorResource(id = R.color.gray),
                fontFamily = Poppins
            )
        )

        ClickableText(
            text = AnnotatedString(stringResource(R.string.forgot_password)),
            style = TextStyle(
                color = colorResource(id = R.color.dark_green),
                fontWeight = FontWeight.SemiBold,
                fontFamily = Poppins,
                textDecoration = TextDecoration.Underline
            ),
            onClick = {
//                TODO: set route to forgot password screen
                navController.navigate("")
            },
            modifier = modifier.align(Alignment.End)
        )

        Button(
            onClick = {
                viewModel.signIn(
                    email.text,
                    password.text
                )
                showDialog(true)},
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
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
        ),
            showDialog = {  }
            ,navController = rememberNavController())
    }
}