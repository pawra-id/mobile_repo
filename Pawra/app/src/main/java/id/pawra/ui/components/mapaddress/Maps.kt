package id.pawra.ui.components.mapaddress

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun Maps(
    mapProperties: MapProperties,
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings,
//    calculateZoneViewCenter: () -> LatLngBounds,
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        // NOTE: Some features of the MarkerInfoWindow don't work currently. See docs:
        // https://github.com/googlemaps/android-maps-compose#obtaining-access-to-the-raw-googlemap-experimental
        // So you can use clusters as an alternative to markers.
        MarkerInfoWindow(
            state = rememberMarkerState(position = LatLng(49.1, -122.5)),
            snippet = "Some stuff",
            onClick = {
                // This won't work :(
                System.out.println("Mitchs_: Cannot be clicked")
                true
            },
            draggable = true
        )
    }
//    // Center camera to include all the Zones.
//    LaunchedEffect(state.clusterItems) {
//        if (state.clusterItems.isNotEmpty()) {
//            cameraPositionState.animate(
//                update = CameraUpdateFactory.newLatLngBounds(
//                    calculateZoneViewCenter(),
//                    0
//                ),
//            )
//        }
//    }
}

/**
 * If you want to center on a specific location.
 */
suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)