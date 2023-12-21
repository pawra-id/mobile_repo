package id.pawra.ui.components.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun SignUpHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pawra_icon),
            contentDescription = stringResource(R.string.pawra_icon),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(100.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(R.string.how_are_you),
            fontSize = 26.sp,
            color = DarkGreen,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
        )
        Text(
            text = stringResource(R.string.sign_you_up),
            fontSize = 26.sp,
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpHeaderPreview() {
    PawraTheme {
        SignUpHeader()
    }
}