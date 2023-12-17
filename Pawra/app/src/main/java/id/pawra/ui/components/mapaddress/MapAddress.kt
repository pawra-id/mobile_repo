package id.pawra.ui.components.mapaddress

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.screen.vet.MapViewModel
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.general.BottomSheet
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.vet.LocationState
import id.pawra.ui.screen.vet.VetViewModel

@Composable
fun MapAddress(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel,
    authViewModel: AuthViewModel,
    state: LocationState.LocationAvailable
) {
    val mapProperties = MapProperties()
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.cameraLatLang, 6.5f)
    }

    authViewModel.getSession()
    val userInfo = authViewModel.sessionState.collectAsState().value

    viewModel.getAddress(
        state.cameraLatLang,
        user = userInfo
    )
    LaunchedEffect(viewModel.currentLatLong) {
        if ((viewModel.currentLatLong.latitude == 0.0) and (viewModel.currentLatLong.longitude == 0.0)) {
            cameraPositionState.animate(CameraUpdateFactory.newLatLng(state.cameraLatLang))
        } else {
            cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
        }

    }

    val textSearch by viewModel.query.collectAsState()

    val isVisible by remember {
        derivedStateOf {
            textSearch.isNotBlank()
        }
    }

    BottomSheet(
        expanded = true,
        content = {
            Column {
                MapAddressTopBar()

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Maps(
                        mapProperties,
                        cameraPositionState,
                        uiSettings,
                        location = state.cameraLatLang
                    )

                    Surface(
                        modifier = Modifier
                            .padding(vertical = 22.dp, horizontal = 15.dp)
                            .align(Alignment.TopCenter)
                            .fillMaxWidth(),
                        color = White,
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SearchBar(
                                query = textSearch,
                                onQueryChange = {
                                    viewModel.setSearchText(it, true)
                                    viewModel.searchPlaces() },
                                onSearch = {},
                                active = false,
                                onActiveChange = {},
                                placeholder = {
                                    Text(
                                        "Search location",
                                        color = Gray
                                    )
                                },
                                trailingIcon = {
                                    if (isVisible) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear",
                                            tint = Gray,
                                            modifier = modifier.clickable {
                                                viewModel.setSearchText("", false)
                                            }
                                        )
                                    }
                                },
                                modifier = modifier.background(White, RoundedCornerShape(15.dp))
                            ) {}

                            AnimatedVisibility(
                                viewModel.locationAutofill.isNotEmpty(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(viewModel.locationAutofill, key = { it.placeId }) {data ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                                .clickable {
                                                    viewModel.setSearchText(data.address, false)
                                                    viewModel.locationAutofill.clear()
                                                    viewModel.getCoordinates(data)
                                                }
                                        ) {
                                            Text(data.address)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        BottomSheetMapContent(
            headAddress = viewModel.headLocation,
            fullAddress = viewModel.location
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MapAddressPreview() {
    PawraTheme {
        MapAddress(
            viewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            authViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            state = LocationState.LocationAvailable(
                LatLng(0.0,0.0)
            )
        )
    }
}