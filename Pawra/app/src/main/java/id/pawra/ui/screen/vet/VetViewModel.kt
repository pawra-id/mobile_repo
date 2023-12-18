package id.pawra.ui.screen.vet

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.VetResponseItem
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.VetRepository
import id.pawra.ui.common.UiState
import id.pawra.ui.components.petactivities.FilterActivities
import id.pawra.ui.components.vets.FilterVets
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class VetViewModel(
    private val vetRepository: VetRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _vetsState: MutableStateFlow<UiState<List<VetResponseItem>>> = MutableStateFlow(
        UiState.Loading)
    val vetsState: StateFlow<UiState<List<VetResponseItem>>>
        get() = _vetsState

    private val _vetDetailState: MutableStateFlow<UiState<VetResponseItem>> = MutableStateFlow(
        UiState.Loading)
    val vetDetailState: StateFlow<UiState<VetResponseItem>>
        get() = _vetDetailState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _location = mutableStateOf(LatLng(0.0,0.0))
    val location: State<LatLng> get() = _location

    fun getVets(keyword: String, filter: String) {
        _query.value = keyword
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            vetRepository.getVets(user, keyword)
                .collect { activities ->
                    when {
                        activities.error != null ->{
                            _vetsState.value = UiState.Error(activities.error)
                        }
                        else -> {
                            _vetsState.value = UiState.Success(
                                filterVets(activities.items ?: listOf(), filter)
                            )
                        }
                    }
                }


        }
    }

    data class Locations(val latitude: Double, val longitude: Double)

    private fun filterVets(list: List<VetResponseItem>, filter: String): List<VetResponseItem>{
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            if (user.latitude.isNotEmpty() and user.longitude.isNotEmpty()) {
                _location.value = LatLng(user.latitude.toDouble(), user.longitude.toDouble())
            }
        }

        val distanceComparator = Comparator<VetResponseItem> { location1, location2 ->
            val loc1 = Locations(location1.latitude?.toDouble() ?: 0.0, location1.longitude?.toDouble() ?: 0.0)
            val loc2 = Locations(location2.latitude?.toDouble() ?: 0.0, location2.longitude?.toDouble() ?: 0.0)
            val distance1 = calculateDistance(_location.value.latitude, _location.value.longitude, loc1)
            val distance2 = calculateDistance(_location.value.latitude, _location.value.longitude, loc2)
            distance1.compareTo(distance2)
        }

        return when(filter){
            FilterVets.Nearest.name -> {
                list.sortedWith(distanceComparator)
            }

            FilterVets.Experience.name -> {
                list.sortedByDescending { it.experience }
            }

            else -> { list }
        }
    }

    private fun calculateDistance(userLat: Double, userLng: Double, location: Locations): Double {
        val earthRadius = 6371000 // meters

        val lat1 = Math.toRadians(userLat)
        val lat2 = Math.toRadians(location.latitude)
        val lon1 = Math.toRadians(userLng)
        val lon2 = Math.toRadians(location.longitude)

        val dLat = lat2 - lat1
        val dLon = lon2 - lon1

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(lat1) * cos(lat2) * sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

    fun getDetailVet(vetId: Int) {
        viewModelScope.launch {
            val user = authRepository.getSession().first()
            if (user.latitude.isNotEmpty() and user.longitude.isNotEmpty()) {
                _location.value = LatLng(user.latitude.toDouble(), user.longitude.toDouble())
            }

            vetRepository.getDetailVet(user, vetId)
                .collect { vetDetail ->
                    when {
                        vetDetail.error != null ->{
                            _vetDetailState.value = UiState.Error(vetDetail.error)
                        }
                        else -> {
                            _vetDetailState.value = UiState.Success(vetDetail)
                        }
                    }
                }
        }
    }
}