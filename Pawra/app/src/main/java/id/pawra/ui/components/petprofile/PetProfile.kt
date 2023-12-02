package id.pawra.ui.components.petprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.PetData
import id.pawra.data.local.preference.SessionModel
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledOrange
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.Orange
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun PetProfile(
    image: String,
    pet: PetData,
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    viewModel.getSession()
    val sessionState by viewModel.sessionState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 22.dp, end = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .border(2.dp, DarkGreen, CircleShape)
                        .size(110.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(pet.image),
                        contentDescription = "Dog Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(
                modifier = modifier
                    .padding(vertical = 20.dp)
            ) {
                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(LightGray)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sex",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DisabledBlue)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pet.gender,
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = DarkBlue,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(vertical = 20.dp)
            ) {
                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(LightGray)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Age",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DisabledOrange)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pet.age.toString(),
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Orange,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(vertical = 20.dp)
            ) {
                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(LightGray)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Neutred",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                fun Boolean.toYesNo(): String = if (this) "Yes" else "No"

                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DisabledGreen)
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pet.neutred.toYesNo(),
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = DarkGreen,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        Box(
            modifier = modifier
                .padding(start = 22.dp, end = 22.dp),
            contentAlignment = Alignment.TopStart
        ){
            Column {
                Text(
                    text = pet.name,
                    color = Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                )

                Text(
                    text = pet.breed,
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 22.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                InfoBox("Color", pet.primaryColor)
            }
            item {
                InfoBox("Weight", "${pet.weight} kg")
            }
            item {
                InfoBox("Height", "${pet.height} cm")
            }
            item {
                InfoBox("Microchip ID", pet.microchipId ?: "")
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .fillMaxWidth()
                .heightIn(min = 130.dp, max = 150.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(DisabledGreen),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    text = "Summary",
                    color = DarkGreen,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = pet.summary ?: "",
                    color = Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 22.dp)
                .fillMaxWidth()
                .heightIn(min = 80.dp, max = 100.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(DisabledBlue),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                when (sessionState) {
                    is UiState.Success -> {
                        val userInfo = (sessionState as UiState.Success<SessionModel>).data

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AsyncImage(
                                model = image,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Column{
                                Text(
                                    text = userInfo.name,
                                    color = Black,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                )

                                Text(
                                    text = "Owner",
                                    color = DarkBlue,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 13.sp,
                                )

                                Text(
                                    text = userInfo.email,
                                    color = Gray,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }

                    is UiState.Error -> {

                        Text(text = "Error: ${(sessionState as UiState.Error).errorMessage}")
                    }

                    else -> {
                        Text(text = "Loading...")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun PetProfilePreview() {
    PawraTheme {
        val petData = PetData(
            "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "Max",
            "Golden Retrivier",
            true,
            1,
            24,
            "Male",
            75,
            "White, Gold",
            "123456",
            "A playful and friendly Corgi."
        )
        PetProfile(
            image = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos:5121efa9bf4f08285ea0d098ce7756aa20230924195603.png",
            pet = petData,
            viewModel = viewModel(
            factory = ViewModelFactory(Injection.provideAuthRepository(LocalContext.current))
        ))
    }
}