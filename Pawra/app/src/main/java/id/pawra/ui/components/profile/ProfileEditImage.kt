package id.pawra.ui.components.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import id.pawra.R
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun ProfileEditImage(
    image: String,
    updateImage: (String) -> Unit,
    isImageUpdate: (Boolean) -> Unit
) {

    val imageUri = rememberSaveable { mutableStateOf(image) }
    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty { R.drawable.ic_user }
    )
    updateImage(imageUri.value)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri.value = it.toString()
            isImageUpdate(true)
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .border(2.dp, DarkGreen, CircleShape)
                    .size(145.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "add profile image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .clickable { launcher.launch("image/*") }
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(36.dp)
                    .background(DisabledGreen, shape = CircleShape),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_edit_24),
                    contentDescription = "Edit Profile Image",
                    tint = (DarkGreen)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileEditImagePreview() {
    PawraTheme {
        ProfileEditImage(
            image = "",
            {},
            {}
        )
    }
}