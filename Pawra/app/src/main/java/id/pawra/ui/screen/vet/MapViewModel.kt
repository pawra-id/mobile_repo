package id.pawra.ui.screen.vet

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng

class MapViewModel: ViewModel() {
    lateinit var fusedLocationClient: FusedLocationProviderClient

    var locationState by mutableStateOf<LocationState>(LocationState.NoPermission)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        locationState = LocationState.LocationLoading
        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                locationState = if (location == null && locationState !is LocationState.LocationAvailable) {
                    LocationState.Error
                } else {
                    LocationState.LocationAvailable(LatLng(location.latitude, location.longitude))
                }
            }
    }

}