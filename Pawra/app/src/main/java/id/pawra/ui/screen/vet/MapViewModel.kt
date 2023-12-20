package id.pawra.ui.screen.vet

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.repository.AuthRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MapViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var placesClient: PlacesClient
    lateinit var geoCoder: Geocoder

    var locationState by mutableStateOf<LocationState>(LocationState.LocationLoading)
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    private var isSearch by mutableStateOf(false)
    var location by mutableStateOf("")
    var headLocation by mutableStateOf("")

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        locationState = LocationState.LocationLoading
        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location == null) {
                    locationState = LocationState.NoPermission
                    if (locationState !is LocationState.LocationAvailable) {
                        locationState = LocationState.Error
                    }
                } else {
                    locationState = LocationState.LocationAvailable(LatLng(location.latitude, location.longitude))
                }
            }
    }

    val locationAutofill = mutableStateListOf<AutocompleteResult>()
    var currentLatLong by mutableStateOf(LatLng(0.0, 0.0))
    private var job: Job? = null

    fun setSearchText(it: String, isSearch: Boolean) {
        _query.value = it
        this.isSearch = isSearch
    }
    @OptIn(FlowPreview::class)
    fun searchPlaces() {
        job?.cancel()
        locationAutofill.clear()
        job = viewModelScope.launch {
            _query.debounce(1000).collect { query ->
                if (isSearch) {
                    val request = FindAutocompletePredictionsRequest
                        .builder()
                        .setQuery(query)
                        .build()
                    placesClient
                        .findAutocompletePredictions(request)
                        .addOnSuccessListener { response ->
                            locationAutofill += response.autocompletePredictions.map {
                                AutocompleteResult(
                                    it.getFullText(null).toString(),
                                    it.placeId
                                )
                            }
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            println(it.cause)
                            println(it.message)
                        }
                }
            }
        }
    }

    fun getCoordinates(result: AutocompleteResult) {
        val placeFields = listOf(Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
        placesClient.fetchPlace(request).addOnSuccessListener {
            if (it != null) {
                currentLatLong = it.place.latLng!!
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    fun getAddress(
        latLng: LatLng,
        user: SessionModel,
    ) {
        viewModelScope.launch {
            val address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            location = address?.get(0)?.getAddressLine(0).toString()
            headLocation = address?.get(0)?.featureName.toString().ifEmpty {
                val commaIndex = location.indexOf(",")
                location.substring(0, commaIndex)
            }

            authRepository.updateProfile(
                id = user.id,
                token = user.token,
                username = user.name,
                email = user.email,
                summary = user.summary,
                address = location,
                image = user.image,
                latitude = latLng.latitude.toString(),
                longitude = latLng.longitude.toString(),
                expire = user.expire
            )
        }
    }
}