package id.pawra.ui.screen.vet

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStates
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import id.pawra.BuildConfig
import id.pawra.data.ViewModelFactory
import id.pawra.di.Injection
import id.pawra.ui.components.dialog.ConfirmationDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.components.mapaddress.MapAddress
import id.pawra.ui.components.mapaddress.MapAddressTopBar
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.PawraTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapAddressScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MapViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
) {

    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    viewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    Places.initialize(context.applicationContext, BuildConfig.GOOGLE_MAPS_API_KEY)
    viewModel.placesClient = Places.createClient(context)
    viewModel.geoCoder = Geocoder(context)

    val locationPermissionState = rememberMultiplePermissionsState(listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ))

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            if (LocationManagerCompat.isLocationEnabled(locationManager)) {
                viewModel.getCurrentLocation()
            } else {
                viewModel.locationState = LocationState.LocationDisabled
            }
        } else {
            viewModel.locationState = LocationState.NoPermission
        }
    }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            showDialog = false
            viewModel.getCurrentLocation()
        }
    }

    AnimatedContent(
        viewModel.locationState, label = ""
    ) { state ->
        when (state) {
            is LocationState.NoPermission -> {
                ConfirmationDialog(
                    headText = "Request Permission",
                    warn = true,
                    message = "We need location permission to continue",
                    setShowDialog = {
                        showDialog = it
                    },
                    action = { locationPermissionState.launchMultiplePermissionRequest() }
                )
            }
            is LocationState.LocationDisabled -> {
                ConfirmationDialog(
                    headText = "Enable Location",
                    warn = true,
                    message = "We need location to continue",
                    setShowDialog = {
                        showDialog = it
                    },
                    action = {
                        requestLocationEnable(context, viewModel, locationLauncher)
                    }
                )
            }
            is LocationState.LocationLoading -> {
                LoadingBox()
            }
            is LocationState.Error -> {
                ConfirmationDialog(
                    headText = "Retry",
                    warn = true,
                    message = "Error fetching your location",
                    setShowDialog = {
                        showDialog = it
                    },
                    action = { viewModel.getCurrentLocation() }
                )
            }
            is LocationState.LocationAvailable -> {
                MapAddress(
                    viewModel = viewModel,
                    state = state,
                    authViewModel = authViewModel
                )
            }
        }
    }
}

fun requestLocationEnable(
    context: Context,
    viewModel: MapViewModel,
    locationLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
) {
    context as Activity
    context.let { it ->
        val locationRequest = LocationRequest.create()
        val builder = LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)

        LocationServices
            .getSettingsClient(it)
            .checkLocationSettings(builder.build())
            .addOnSuccessListener {
                if (it.locationSettingsStates?.isLocationPresent == true) {
                    viewModel.getCurrentLocation()
                }
            }
            .addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest.Builder(it.resolution).build()
                        locationLauncher.launch(intentSenderRequest)

                    } catch (e : IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
            }
    }
}

@Composable
@Preview(showBackground = true)
fun MapAddressScreenPreview() {
    PawraTheme {
        MapAddressScreen(
            navController = rememberNavController(),
            viewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}