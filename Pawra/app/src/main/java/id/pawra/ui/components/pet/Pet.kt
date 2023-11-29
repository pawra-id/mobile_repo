package id.pawra.ui.components.pet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.data.local.preference.PetData
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun Pet(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pets = listOf(
        PetData(
            "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "Max",
            "Corgi",
            true,
            1,
            24,
            "Male",
            75,
            "White, Gold",
            "123456",
            "A playful and friendly Corgi."

        ),
        PetData(
            "https://static.vecteezy.com/system/resources/previews/005/857/330/large_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "Diana",
            "Cairn Terier",
            false,
            2,
            25,
            "Female",
            65,
            "Brown",
            "789012",
            ""
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        pets.forEach { pet ->
            item {
                Box(
                    modifier = modifier
                        .height(150.dp)
                        .width(150.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable {
                            navController.navigate(Screen.PetProfile.route)
                        },
                ) {
                    PetItem(
                        image = pet.image,
                        gender = pet.gender,
                        name = pet.name,
                        modifier = modifier
                    )
                }
            }
        }

        item {
            Box(
                modifier = modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
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
}

@Composable
fun PetItem(
    image: String,
    gender: String,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(15.dp))
        ) {
            AsyncImage(
                model = image,
                contentDescription = name,
                modifier = modifier
                    .fillMaxSize()
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
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = if (gender == "Male") DisabledBlue else DisabledPink)
                        .padding(vertical = 2.dp, horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PetPreview() {
    PawraTheme {
        Pet(navController = rememberNavController())
    }
}