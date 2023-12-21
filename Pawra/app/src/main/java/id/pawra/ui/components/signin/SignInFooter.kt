package id.pawra.ui.components.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun SignInFooter(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoNotHaveAccountText(
            navController = navController
        )
    }
}

@Composable
fun DoNotHaveAccountText(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val str = stringResource(R.string.dont_have_account)
    val startSignInIndex = str.indexOf("Sign Up")
    val endSignInIndex = startSignInIndex + 7

    val annotatedString = buildAnnotatedString {
        append(str)
        addStyle(
            style = SpanStyle(color = Gray
            ), start = 0, end = startSignInIndex - 1
        )
        addStyle(
            style = SpanStyle(
                color = DarkGreen,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            ), start = startSignInIndex, end = endSignInIndex
        )
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(fontFamily = Poppins),
        onClick = {
            navController.navigate(Screen.SignUp.route) {
                popUpTo(Screen.SignIn.route) {
                    inclusive = true
                }
            }
        },
        modifier = modifier.padding(vertical = 20.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun SignInFooterPreview() {
    PawraTheme {
        SignInFooter(navController = rememberNavController())
    }
}