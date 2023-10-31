package id.pawra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import id.pawra.R
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpForm(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val name = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

        Text(
            text = "Name",
            color = colorResource(id = R.color.gray),
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())
        OutlinedTextField(
            value = name.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { name.value = it },
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
            text = "Email",
            color = colorResource(id = R.color.gray),
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())
        OutlinedTextField(
            value = email.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email.value = it },
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
            text = "Password",
            color = colorResource(id = R.color.gray),
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        var showPassword by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password.value,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }, trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_24), "Show Password")
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_off_24), "Hide Password")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it },
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
            text = "Confirm Password",
            color = colorResource(id = R.color.gray),
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        var showConfirmPassword by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = confirmPassword.value,
            visualTransformation = if (showConfirmPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }, trailingIcon = {
                if (showConfirmPassword) {
                    IconButton(onClick = { showConfirmPassword = false }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_24), "Show Password")
                    }
                } else {
                    IconButton(onClick = { showConfirmPassword = true }) {
                        Icon(painterResource(id = R.drawable.baseline_visibility_off_24), "Hide Password")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { confirmPassword.value = it },
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

        TermAndServiceText()
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TermAndServiceText(
    modifier: Modifier = Modifier
) {
    val str = "Accept terms & services"
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
            style = SpanStyle(color = colorResource(id = R.color.gray)
            ), start = startAcceptIndex, end = endAcceptIndex
        )
        addStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.dark_green),
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            ), start = startTermsIndex, end = endTermsIndex
        )
        addStyle(
            style = SpanStyle(color = colorResource(id = R.color.gray)
            ), start = startIndex, end = endIndex
        )
        addStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.dark_green),
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

    val isChecked = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it })

        val uriHandler = LocalUriHandler.current
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getUrlAnnotations(offset, offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item.url)
                }
            }
        )
    }

}

@Composable
@Preview(showBackground = true)
fun SignUpFormPreview() {
    PawraTheme {
        SignUpForm()
    }
}