package id.pawra.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun ListDog(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val image = "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg"
    val name = "Max"
    val type = "Retriever"
    val gender = "Male"
    val age = 0.6

    Row(
        modifier = modifier
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            Box(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        navController.navigate(Screen.PetProfile.route)
                    },
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    modifier = modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = modifier
                        .padding(5.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = gender,
                        fontSize = 11.sp,
                        color = if (gender == "Male") DarkBlue else DarkPink,
                        modifier = modifier
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(color = if (gender == "Male") DisabledBlue else DisabledPink)
                            .padding(vertical = 2.dp, horizontal = 10.dp)
                    )
                }

            }
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Black,
                modifier = modifier.padding(top = 5.dp)
            )
            Text(
                text = type,
                fontSize = 11.sp,
                color = Gray,
            )
            Text(
                text = stringResource(id = R.string.dog_age, age),
                fontSize = 11.sp,
                color = DarkGreen,
                modifier = modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = DisabledGreen)
                    .padding(vertical = 2.dp, horizontal = 10.dp),
            )
        }

        Box(
            modifier = modifier
                .height(90.dp)
                .width(90.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = DisabledGreen)
                .clickable {

                },
        ) {
            Icon(
                Icons.Filled.Add,
                "Add Dog",
                tint = LightGreen,
                modifier = modifier
                    .size(44.dp)
                    .align(Alignment.Center)
            )
        }
    }


}

@Composable
@Preview(showBackground = true)
fun ListDogPreview() {
    PawraTheme {
        ListDog(
            navController = rememberNavController()
        )
    }
}