package id.pawra.ui.components.mapaddress

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.screen.vet.VetViewModel
import id.pawra.utils.CustomMarker

@Composable
fun Maps(
    mapProperties: MapProperties,
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings,
    vetViewModel: VetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    location: LatLng,

) {
    vetViewModel.getVets("", "")
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings
    ) {
        Marker(
            state = rememberMarkerState(position = location),
            title = "Your location",
            icon = CustomMarker.bitmapDescriptor(LocalContext.current, R.drawable.map_icon)
        )

        vetViewModel.vetsState.collectAsState().value.let { vetsState ->
            when (vetsState) {
                is UiState.Success -> {
                    vetsState.data.forEach { data ->
                        Marker(
                            state = rememberMarkerState(position =
                                LatLng(
                                    data.latitude?.toDouble() ?: 0.0,
                                    data.longitude?.toDouble() ?: 0.0
                                )
                            ),
                            title = data.name ?: "",
                            snippet = data.address ?: "",
                        )
                    }
                }

                else -> {}
            }
        }
    }
}