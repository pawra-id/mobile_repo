package id.pawra.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun Welcome(
    image: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 22.dp, vertical = 10.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = image,
            contentDescription = "Profile picture",
            modifier = modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = modifier.padding(start = 10.dp).weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hi! Pawra",
                fontSize = 16.sp,
                color = colorResource(id = R.color.black),
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
            )
            Text(
                text = "Welcome Back",
                fontSize = 10.sp,
                color = colorResource(id = R.color.gray),
                fontFamily = Poppins,
                modifier = modifier
            )
        }

        IconButton(
            onClick = { /* Handle setting click */ },
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                tint = colorResource(id = R.color.black)
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun WelcomePreview() {
    PawraTheme {
        Welcome("a")
    }
}