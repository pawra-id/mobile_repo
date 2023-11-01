package id.pawra.ui.components.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.ui.navigation.Screen
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
        Button(
            onClick = { },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.sign_in), fontSize = 14.sp)
        }
        Text(
            text = "or sign in with",
            fontSize = 14.sp,
            color = colorResource(id = R.color.gray),
            modifier = modifier.padding(top = 25.dp)
        )
        Row(
            modifier = modifier
                .padding(horizontal = 70.dp, vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = stringResource(R.string.google_icon),
                modifier = modifier.size(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.twitter_icon),
                contentDescription = stringResource(R.string.google_icon),
                modifier = modifier.size(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.facebook_icon),
                contentDescription = stringResource(R.string.google_icon),
                modifier = modifier.size(30.dp)
            )
        }

        DontHaveAccountText(
            navController = navController
        )
    }
}

@Composable
fun DontHaveAccountText(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val str = stringResource(R.string.dont_have_account)
    val startSignInIndex = str.indexOf("Sign Up")
    val endSignInIndex = startSignInIndex + 7

    val annotatedString = buildAnnotatedString {
        append(str)
        addStyle(
            style = SpanStyle(color = colorResource(id = R.color.gray)
            ), start = 0, end = startSignInIndex - 1
        )
        addStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.dark_green),
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            ), start = startSignInIndex, end = endSignInIndex
        )
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(fontFamily = Poppins),
        onClick = {
            navController.navigate(Screen.SignUp.route)
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