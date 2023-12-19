package id.pawra.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.vets.FilterVets
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.vet.VetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun NearbyVets(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: VetViewModel
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(Unit) {
        viewModel.getVets("", FilterVets.Nearest.name)
    }
    var activeVet by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nearby Vets",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                modifier = modifier.weight(1f)
            )

            Text(
                text = "more",
                fontWeight = FontWeight.SemiBold,
                color = Gray, fontFamily = Poppins, fontSize = 16.sp,
                modifier = modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        navController.navigate(Screen.Vets.route)
                    }
            )
        }
        viewModel.vetsState.collectAsState().value.let { vetsState ->
            when (vetsState) {
                is UiState.Success -> {
                    LaunchedEffect(Unit){
                        if (vetsState.data.isNotEmpty()) {
                            activeVet = vetsState.data[0].id
                            viewModel.getDetailVet(vetsState.data[0].id)
                        }
                    }

                    LazyRow(
                        modifier = modifier
                            .padding(vertical = 10.dp, horizontal = 0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        items(vetsState.data.take(10), key = { it.id }) { data ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = modifier
                                    .size(60.dp)
                                    .clip(shape = CircleShape)
                                    .border(
                                        if (activeVet == data.id) 5.dp else (1.5).dp,
                                        LightGreen,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        activeVet = data.id
                                        viewModel.getDetailVet(data.id)
                                    }
                            ) {
                                Card(
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(50.dp)
                                ) {
                                    AsyncImage(
                                        model = data.image,
                                        placeholder = painterResource(id = R.drawable.ic_user),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .size(50.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {}
            }
        }

        viewModel.vetDetailState.collectAsState().value.let { vetDetailState ->
            when (vetDetailState) {
                is UiState.Success -> {
                    Box(
                        modifier = modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .width(screenWidth)
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(color = DisabledGreen),
                    ) {
                        Column(
                            modifier = modifier
                                .padding(horizontal = 24.dp, vertical = 14.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = vetDetailState.data.name ?: "",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkGreen
                            )
                            Text(
                                text =  vetDetailState.data.address ?: "",
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = DarkGreen
                            )
                            Text(
                                text = vetDetailState.data.description ?: "",
                                fontSize = 13.sp,
                                color = DarkGreen,
                                modifier = modifier.padding(top = 10.dp)
                            )
                            Button(
                                onClick = {
                                    navController.navigate(Screen.VetProfile.createRoute(vetDetailState.data.id))
                                },
                                modifier = modifier
                                    .padding(top = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkGreen
                                ),
                            ) {
                                Text(
                                    text = "Consult",
                                    fontFamily = Poppins,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NearbyVetsPreview() {
    PawraTheme {
        NearbyVets(
            navController = rememberNavController(),
            viewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}