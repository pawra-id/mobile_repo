package id.pawra.ui.components.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
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
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.auth.SignInFormEvent
import id.pawra.ui.screen.auth.SignUpFormEvent
import id.pawra.ui.screen.auth.SignUpFormState
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    showDialog: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state = viewModel.stateSignUp

        Text(
            text = stringResource(R.string.name),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())
        OutlinedTextField(
            value = state.name,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onEventSignUp(SignUpFormEvent.NameChanged(it)) },
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
            text = stringResource(R.string.email),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())
        OutlinedTextField(
            value = state.email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { viewModel.onEventSignUp(SignUpFormEvent.EmailChanged(it)) },
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
                if (state.emailError != null) {
                    Text(
                        text = state.emailError,
                        color = Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            },
            isError = state.emailError != null
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
                        Icon(painterResource(id = R.drawable.baseline_visibility_24), stringResource(R.string.show_password))
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_off_24), stringResource(R.string.hide_password))
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onEventSignUp(SignUpFormEvent.PasswordChanged(it)) },
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

        Text(
            text = stringResource(R.string.confirm_password),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        var showConfirmPassword by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = state.repeatedPassword,
            visualTransformation = if (showConfirmPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }, trailingIcon = {
                if (showConfirmPassword) {
                    IconButton(onClick = { showConfirmPassword = false }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_24), stringResource(R.string.show_password))
                    }
                } else {
                    IconButton(onClick = { showConfirmPassword = true }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_off_24), stringResource(R.string.hide_password))
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onEventSignUp(SignUpFormEvent.RepeatedPasswordChanged(it)) },
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
                if (state.repeatedPasswordError != null) {
                    Text(
                        text = state.repeatedPasswordError,
                        color = Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            },
            isError = state.repeatedPasswordError != null
        )

        TermAndServiceText(
            state = state,
            viewModel = viewModel
        )

        Button(
            onClick = {
                viewModel.onEventSignUp(SignUpFormEvent.Submit)
                showDialog(viewModel.showDialog)},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.sign_up), fontSize = 14.sp)
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TermAndServiceText(
    modifier: Modifier = Modifier,
    state: SignUpFormState,
    viewModel: AuthViewModel
) {
    val str = stringResource(R.string.accept_terms_services)
    val startAcceptIndex = str.indexOf("Accept")
    val endAcceptIndex = startAcceptIndex + 6
    val startIndex = str.indexOf("&")
    val endIndex = startIndex + 1
    val startTermsIndex = str.indexOf("terms")
    val endTermsIndex = startTermsIndex + 5
    val startServicesIndex = str.indexOf("services")
    val endServicesIndex = startServicesIndex + 8

    val annotatedString = buildAnnotatedString {
        append(str)
        addStyle(
            style = SpanStyle(color = Gray
            ), start = startAcceptIndex, end = endAcceptIndex
        )
        addStyle(
            style = SpanStyle(
                color = DarkGreen,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            ), start = startTermsIndex, end = endTermsIndex
        )
        addStyle(
            style = SpanStyle(color = Gray
            ), start = startIndex, end = endIndex
        )
        addStyle(
            style = SpanStyle(
                color = DarkGreen,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            ), start = startServicesIndex, end = endServicesIndex
        )

        addUrlAnnotation(
            UrlAnnotation("https://www.dicoding.com/index.php/termsofuse"),
            start = startTermsIndex,
            end = endTermsIndex
        )

        addUrlAnnotation(
            UrlAnnotation("https://help.dicoding.com/academy-dicoding/terms-of-use-fitur-submission-dicoding-indonesia/"),
            start = startServicesIndex,
            end = endServicesIndex
        )
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier.fillMaxWidth().offset(x = (-12).dp)
        ) {
            Checkbox(
                checked = state.acceptedTerms,
                onCheckedChange = { viewModel.onEventSignUp(SignUpFormEvent.AcceptTerms(it)) })

            val uriHandler = LocalUriHandler.current
            ClickableText(text = annotatedString, style = TextStyle(fontFamily = Poppins), onClick = { offset ->
                annotatedString.getUrlAnnotations(offset, offset)
                    .firstOrNull()?.let { annotation ->
                        uriHandler.openUri(annotation.item.url)
                    }
            }
            )
        }
        if (state.termsError != null) {
            Text(
                text = state.termsError,
                color = Red,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpFormPreview() {
    PawraTheme {
        SignUpForm(viewModel = viewModel(
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))),
            showDialog = {  })
    }
}