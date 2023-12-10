package id.pawra.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import id.pawra.R
import id.pawra.ui.theme.PawraTheme

@Composable
fun ProfileImage (
    image: String
) {
    val painter = rememberImagePainter(
        image.ifEmpty { R.drawable.ic_user }
    )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            Image(
                painterResource(id = R.drawable.background_profileimage),
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp),
                contentDescription = "Background Profile Image",
            )
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(18.dp)
                    .size(130.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .size(130.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileImagePreview() {
    PawraTheme {
        ProfileImage(
            image = ""
        )
    }
}