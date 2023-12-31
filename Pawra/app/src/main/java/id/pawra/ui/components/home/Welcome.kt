package id.pawra.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import id.pawra.R
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun Welcome(
    image: String,
    name: String,
    modifier: Modifier = Modifier
) {

    val painter = rememberAsyncImagePainter(image.ifEmpty { R.drawable.ic_user }
    )
    
    Row(
        modifier = modifier
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 20.dp,
                bottom = 20.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = "Profile picture",
            modifier = modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = modifier
                .padding(start = 10.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.hi_pawra, name),
                fontSize = 16.sp,
                color = Black,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    ),
                )
            )
            Text(
                text = "Welcome Back",
                fontSize = 10.sp,
                color = Gray,
                fontFamily = Poppins,
                modifier = modifier.height(14.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomePreview() {
    PawraTheme {
        Welcome("", "Alya")
    }
}