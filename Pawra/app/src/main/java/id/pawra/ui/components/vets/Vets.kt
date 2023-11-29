package id.pawra.ui.components.vets

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import id.pawra.R
import id.pawra.ui.common.NoRippleTheme
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.components.petactivities.FilterActivities
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red
import id.pawra.ui.theme.White

@Composable
fun Vets(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val query by remember { mutableStateOf("") }
    var activeFilter by remember { mutableStateOf(FilterVets.Nearest.name) }

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
                onQueryChange = {  },
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            (0..5).forEach { _ ->
                Row(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(15.dp))
                        .background(White)
                        .border(1.dp, LightGray, RoundedCornerShape(15.dp))
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    AsyncImage(
                        model = "https://2.bp.blogspot.com/-_sOpXiMO0m4/ViVULhN611I/AAAAAAAAMCk/LbqKTS7T9Fw/s1600/indah%2Bkusuma%2Bhot%2Bdoctor%2Bindonesia.jpg",
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
                            text = "drh. Humberto Chavez",
                            fontSize = 13.sp,
                            color = Black,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Text(
                            text = "Klinik Hewan Purnama",
                            fontSize = 12.sp,
                            color = Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Jl. Karma No 4, Sekarmadu, Jogjakarta",
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
                                text = "2.3 km",
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
                                text = "9am - 3pm",
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

}

@Composable
@Preview(showBackground = true)
fun VetsPreview() {
    PawraTheme {
        Vets(
            navController = rememberNavController()
        )
    }
}