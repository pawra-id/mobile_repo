package id.pawra.ui.components.vets

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.NoRippleTheme
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.vet.VetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun Vets(
    modifier: Modifier = Modifier,
    navController: NavController,
    vetViewModel: VetViewModel
) {
    val query by remember { vetViewModel.query }
    var activeFilter by remember { mutableStateOf(FilterVets.Nearest.name) }

    LaunchedEffect(Unit){
        vetViewModel.getVets("", activeFilter)
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

    Column(
        modifier = modifier
            .padding(horizontal = 22.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = modifier
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            SearchBar(
                query = query,
                onQueryChange = { vetViewModel.getVets(it, filter = activeFilter) },
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Gray
                    )
                },
                placeholder = {
                    Text(
                        "Search vets",
                        color = Gray
                    )
                },
                modifier = modifier.weight(1f)
            ) {}

            IconButton(
                onClick = {
                    navController.navigate(Screen.MapAddress.route)
                },
                modifier = modifier
                    .background(LightGreen, RoundedCornerShape(10.dp))
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Filled.LocationOn,
                    "Add Location",
                    tint = DarkGreen,
                    modifier = modifier
                )
            }
        }

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {
                for (filter in FilterVets.entries) {
                    Box(
                        modifier = modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(if (activeFilter == filter.name) LightGreen else White)
                            .border(
                                2.dp, if (activeFilter == filter.name) LightGreen else LightGray,
                                RoundedCornerShape(20.dp)
                            )
                            .padding(vertical = 5.dp, horizontal = 25.dp)
                            .clickable {
                                activeFilter = filter.name
                                vetViewModel.getVets(query, filter = activeFilter)
                            }
                    ) {
                        Text(
                            text = filter.name,
                            fontSize = 13.sp,
                            color = if (activeFilter == filter.name) DarkGreen else LightGray,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }

        vetViewModel.vetsState.collectAsState().value.let { vetsState ->
            when (vetsState) {
                is UiState.Loading -> {
                    isLoading = true
                }

                is UiState.Success -> {
                    isLoading = false

                    val myLocation = Location("myLocation").apply {
                        latitude = vetViewModel.location.value.latitude
                        longitude = vetViewModel.location.value.longitude
                    }

                    val listState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()

                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(top = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        state = listState
                    ) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }

                        items(vetsState.data, key = { it.id }) { data ->
                            val vetLocation = Location("vetLocation").apply {
                                latitude = data.latitude?.toDouble() ?: 0.0
                                longitude = data.longitude?.toDouble() ?: 0.0
                            }

                            val distanceInMeters = haversineDistance(myLocation, vetLocation)
                            val distanceInKilometers = "%.2f".format(distanceInMeters / 1000)
                            Row(
                                modifier = modifier
                                    .clip(shape = RoundedCornerShape(15.dp))
                                    .background(White)
                                    .border(1.dp, LightGray, RoundedCornerShape(15.dp))
                                    .clickable {
                                        navController.navigate(Screen.VetProfile.createRoute(data.id))
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 15.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                AsyncImage(
                                    model = data.image,
                                    contentDescription = "Vets Name",
                                    modifier = modifier
                                        .size(90.dp)
                                        .clip(RoundedCornerShape(15.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = modifier
                                ) {
                                    Text(
                                        text = data.name ?: "",
                                        fontSize = 13.sp,
                                        color = Black,
                                        fontWeight = FontWeight.SemiBold,
                                    )

                                    Text(
                                        text = data.clinicName ?: "",
                                        fontSize = 12.sp,
                                        color = Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = data.address ?: "",
                                        fontSize = 10.sp,
                                        color = Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = modifier.padding(bottom = 10.dp)
                                    )

                                    Row(
                                        modifier = modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                                    ) {
                                        Text(
                                            text = "$distanceInKilometers km",
                                            fontSize = 10.sp,
                                            color = DarkGreen,
                                            modifier = modifier
                                                .clip(shape = RoundedCornerShape(15.dp))
                                                .background(
                                                    color = DisabledGreen
                                                )
                                                .padding(vertical = 2.dp, horizontal = 10.dp),
                                        )

                                        Text(
                                            text = "${data.startWorkHour} - ${data.endWorkHour}",
                                            fontSize = 10.sp,
                                            color = DarkGreen,
                                            modifier = modifier
                                                .clip(shape = RoundedCornerShape(15.dp))
                                                .background(
                                                    color = DisabledGreen
                                                )
                                                .padding(vertical = 2.dp, horizontal = 10.dp),
                                        )
                                    }

                                }
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    if(showDialog) {
                        isLoading = false
                        ResultDialog(
                            success = false,
                            message = vetsState.errorMessage,
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

}


const val earthRadiusInMeters = 6371000

fun haversineDistance(location1: Location, location2: Location): Double {
    val lat1 = Math.toRadians(location1.latitude)
    val lng1 = Math.toRadians(location1.longitude)
    val lat2 = Math.toRadians(location2.latitude)
    val lng2 = Math.toRadians(location2.longitude)

    val deltaLat = lat2 - lat1
    val deltaLng = lng2 - lng1

    val a = sin(deltaLat / 2) * sin(deltaLat / 2) +
            cos(lat1) * cos(lat2) * sin(deltaLng / 2) * sin(deltaLng / 2)
    val c = 2 * asin(sqrt(a))

    return c * earthRadiusInMeters
}

@Composable
@Preview(showBackground = true)
fun VetsPreview() {
    PawraTheme {
        Vets(
            navController = rememberNavController(),
            vetViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}