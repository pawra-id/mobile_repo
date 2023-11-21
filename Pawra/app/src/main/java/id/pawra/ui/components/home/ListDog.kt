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
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun ListDog(
    image: String,
    name: String,
    type: String,
    gender: String,
    age: Double,
    modifier: Modifier = Modifier
) {
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
                    modifier = modifier.padding(5.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = gender,
                        fontSize = 11.sp,
                        color = colorResource(id = if (gender == "Male") R.color.dark_blue else R.color.dark_pink),
                        modifier = modifier
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(
                                color = colorResource(
                                    id =
                                    if (gender == "Male") R.color.light_blue else R.color.light_pink
                                )
                            )
                            .padding(vertical = 2.dp, horizontal = 10.dp)
                    )
                }

            }
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = colorResource(id = R.color.black),
                modifier = modifier.padding(top = 5.dp)
            )
            Text(
                text = type,
                fontSize = 11.sp,
                color = colorResource(id = R.color.gray),
            )
            Text(
                text = stringResource(id = R.string.dog_age, age),
                fontSize = 11.sp,
                color = colorResource(id = R.color.dark_green),
                modifier = modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(
                        color = colorResource(id = R.color.disabled_green)
                    )
                    .padding(vertical = 2.dp, horizontal = 10.dp),
            )
        }

        Box(
            modifier = modifier
                .height(90.dp)
                .width(90.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.disabled_green))
                .clickable{

                },
        ) {
            Icon(
                Icons.Filled.Add,
                "Add Dog",
                tint = LightGreen,
                modifier = modifier.size(44.dp).align(Alignment.Center)
            )
        }
    }


}

@Composable
@Preview(showBackground = true)
fun ListDogPreview() {
    PawraTheme {
        ListDog(
            "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "Max",
            "Retriever",
            "Male",
            0.6
        )
    }
}