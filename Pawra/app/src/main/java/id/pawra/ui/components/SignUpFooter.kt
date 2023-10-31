package id.pawra.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.PawraTheme

@Composable
fun SignUpFooter(
    modifier: Modifier = Modifier,
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
            Text(text = "Sign Up", fontSize = 14.sp)
        }
        HaveAccountText()
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun HaveAccountText(
    modifier: Modifier = Modifier
) {
    val str = "Have an account? Sign In"
    val startSignInIndex = str.indexOf("Sign In")
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

    ClickableText(text = annotatedString, onClick = { offset ->

    }, modifier = modifier.padding(vertical = 20.dp))
}

@Composable
@Preview(showBackground = true)
fun SignUpFooterPreview() {
    PawraTheme {
        SignUpFooter()
    }
}