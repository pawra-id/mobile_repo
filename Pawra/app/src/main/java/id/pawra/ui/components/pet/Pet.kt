package id.pawra.ui.components.pet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun Pet(
    modifier: Modifier = Modifier,
    navController: NavController,
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {

    LaunchedEffect(Unit){
        petViewModel.getDog()
    }

    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    if (isLoading) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    petViewModel.petState.collectAsState().value.let { petState ->
        when (petState) {
            is UiState.Success -> {
                isLoading = false
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(petState.data, key = { it.id }) { data ->
                        Box(
                            modifier = modifier
                                .height(150.dp)
                                .width(150.dp)
                                .clip(shape = RoundedCornerShape(15.dp))
                                .background(White)
                                .border(1.dp, LightGray, RoundedCornerShape(15.dp))
                                .clickable {
                                    navController.navigate(Screen.PetProfile.createRoute(data.id))
                                },
                        ) {
                            PetItem(
                                image = data.image ?: "",
                                gender = data.gender ?: "",
                                name = data.name ?: "",
                                modifier = modifier
                            )
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
                                    navController.navigate(Screen.PetAdd.route)
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

            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Error -> {
                if (showDialog) {
                    isLoading = false
                    ResultDialog(
                        success = false,
                        message = petState.errorMessage,
                        setShowDialog = {
                            showDialog = it
                        }
                    )
                }
            }
            else -> {}
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

    val painter = rememberAsyncImagePainter(image.ifEmpty { R.drawable.ic_pet })

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(15.dp))
        ) {
            Image(
                painter = painter,
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
                    color = if (gender == "male") DarkBlue else DarkPink,
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = if (gender == "male") DisabledBlue else DisabledPink)
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