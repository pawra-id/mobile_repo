package id.pawra.ui.components.vetprofile

import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.data.remote.response.VetResponseItem
import id.pawra.ui.components.vets.haversineDistance
import id.pawra.ui.screen.vet.VetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledYellow
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.Orange
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.White
import id.pawra.utils.CustomMarker

@Composable
fun VetProfile(
    vet: VetResponseItem,
    vetViewModel: VetViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val location = LatLng(vet.latitude?.toDouble() ?: 0.0, vet.longitude?.toDouble() ?: 0.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 10f)
    }
    val mapProperties = MapProperties()
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false,
        scrollGesturesEnabled = false,
        zoomGesturesEnabled = false
    )

    val myLocation = Location("myLocation").apply {
        latitude = vetViewModel.location.value.latitude
        longitude = vetViewModel.location.value.longitude
    }

    val vetLocation = Location("vetLocation").apply {
        latitude = vet.latitude?.toDouble() ?: 0.0
        longitude = vet.longitude?.toDouble() ?: 0.0
    }

    val distanceInMeters = haversineDistance(myLocation, vetLocation)
    val distanceInKilometers = "%.2f".format(distanceInMeters / 1000)

    Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
    )
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 22.dp, end = 22.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .border(2.dp, DarkGreen, CircleShape)
                        .size(150.dp)
                ) {
                    AsyncImage(
                        model = vet.image,
                        contentDescription = vet.name,
                        modifier = modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(
                modifier = modifier
                    .padding(vertical = 20.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {
                Row(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DisabledBlue)
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.LocationOn,
                        "Location Icon",
                        modifier
                            .padding(end = 5.dp)
                            .size(20.dp),
                        DarkBlue
                        )

                    Text(
                        text = "$distanceInKilometers km",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = DarkBlue,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Row(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(LightGray)
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Work,
                        "Work Icon",
                        modifier
                            .padding(end = 5.dp)
                            .size(20.dp),
                        Black
                    )

                    Text(
                        text = "${vet.experience} years",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Black,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Row(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(DisabledYellow)
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.AccessTimeFilled,
                        "Time Icon",
                        modifier
                            .padding(end = 5.dp)
                            .size(20.dp),
                        Orange
                    )

                    Text(
                        text = "${vet.startWorkHour} - ${vet.endWorkHour}",
                        fontFamily = Poppins,
                        fontSize = 11.sp,
                        color = Orange,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        Box(
            modifier = modifier
                .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 10.dp),
            contentAlignment = Alignment.TopStart
        ){
            Column {
                Text(
                    text = vet.name ?: "",
                    color = Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )

                Text(
                    text = vet.clinicName ?: "",
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 10.dp),
             
        ) {
            Button(
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                String.format(
                                    "https://api.whatsapp.com/send?phone=%s",
                                    "${vet.phone}"
                                )
                            )
                        )
                    )
                },
                border = BorderStroke(2.dp, DarkGreen),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DisabledGreen
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Whatsapp,
                    contentDescription = "Whatsapp Icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = DarkGreen
                )
                Text(
                    text = "WhatsApp",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkGreen
                )
            }
            Button(
                onClick = {
                    val emailIntent = Intent(
                        Intent.ACTION_SEND
                    )
                    emailIntent.type = "vnd.android.cursor.item/email"
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("${vet.email}"))
                    context.startActivity(
                        emailIntent
                    )
                },
                border = BorderStroke(2.dp, DarkGreen),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = White
                )
                Text(
                    text = "Email",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = White
                )
            }
        }

        Box(
            modifier = modifier
                .padding(start = 22.dp, end = 22.dp, bottom = 15.dp)
                .background(DisabledBlue, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.TopStart
        ){
            Row(
                modifier = modifier.padding(vertical = 15.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.graduation_cap_icon),
                    contentDescription = "Location Icon",
                    modifier = modifier
                        .padding(horizontal = 10.dp)
                        .size(35.dp),
                    colorFilter = ColorFilter.tint(
                        DarkBlue
                    )
                )

                Column {
                    Row {
                        Text(
                            text = "\u2022",
                            style = TextStyle().copy(textAlign = TextAlign.Center),
                            modifier = modifier.padding(end = 10.dp),
                            color = Black
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = vet.education ?: "",
                            color = Black,
                            textAlign = TextAlign.Start,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        Column(
            modifier = modifier
                .padding(horizontal = 22.dp, vertical = 15.dp)
                .background(White, RoundedCornerShape(10.dp))
                .border(2.dp, DarkGreen, RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier
                    .height(350.dp)
                    .padding(15.dp)
                    .border(1.dp, LightGray, RoundedCornerShape(10.dp))
                    .fillMaxWidth()
            ) {
                GoogleMap(
                    properties = mapProperties,
                    cameraPositionState = cameraPositionState,
                    uiSettings = uiSettings,
                    modifier = modifier
                        .fillMaxHeight()
                        .border(1.dp, LightGray, RoundedCornerShape(10.dp))
                ) {

                    Marker(
                        state = rememberMarkerState(position = location),
                        title = vet.name ?: "",
                        snippet = vet.address ?: "",
                        icon = CustomMarker.bitmapDescriptor(context, R.drawable.map_icon)
                    )
                }
            }

            Text(
                text = vet.address ?: "",
                fontSize = 13.sp,
                textAlign = TextAlign.Justify,
                lineHeight = 18.sp,
                color = Black,
                modifier = modifier
                    .padding(horizontal = 15.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VetProfilePreview() {
    PawraTheme {
        VetProfile(
            vet = VetResponseItem(id=0),
            vetViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            navController = rememberNavController()
        )
    }
}