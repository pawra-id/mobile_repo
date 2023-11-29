package id.pawra.ui.components.mapaddress

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.UiSettings
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.components.petactivities.PetActivitiesTopBar
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun MapAddress(
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current

    val mapProperties = MapProperties()
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false
    )
    val cameraPositionState = rememberCameraPositionState()

    BottomSheet(
        content = {
            Column {
                MapAddressTopBar()

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Maps(
                        mapProperties,
                        cameraPositionState,
                        uiSettings
                    )

                    Row(
                        modifier = modifier
                            .padding(vertical = 22.dp, horizontal = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {

                        SearchBar(
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            placeholder = {
                                Text(
                                    "Search location",
                                    color = Gray
                                )
                            },
                            modifier = modifier.background(White, RoundedCornerShape(15.dp)).weight(1f)
                        ) {}

                        IconButton(
                            onClick = {

                            },
                            modifier = modifier
                                .background(White, RoundedCornerShape(10.dp))
                                .size(56.dp)
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                "Add Activity",
                                tint = Gray,
                                modifier = modifier
                            )
                        }
                    }
                }
            }
        }
    ) {
        BottomSheetContent()
    }
}

@Composable
@Preview(showBackground = true)
fun MapAddressPreview() {
    PawraTheme {
        MapAddress()
    }
}