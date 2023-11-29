package id.pawra.ui.screen.vet

import android.location.Location
import com.google.android.gms.maps.model.LatLng

sealed class LocationState {
    data object NoPermission: LocationState()
    data object LocationDisabled: LocationState()
    data object LocationLoading: LocationState()
    data class LocationAvailable(val location: LatLng): LocationState()
    data object Error: LocationState()
}