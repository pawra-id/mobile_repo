package id.pawra.ui.components.vetprofile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import coil.compose.rememberImagePainter
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.VetData
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledOrange
import id.pawra.ui.theme.DisabledYellow
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.Orange
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.White

@Composable
fun VetProfile(
    vet: VetData,
    viewModel: AuthViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val mapProperties = MapProperties()
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false
    )
    val cameraPositionState = rememberCameraPositionState()

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
                        model = "https://2.bp.blogspot.com/-_sOpXiMO0m4/ViVULhN611I/AAAAAAAAMCk/LbqKTS7T9Fw/s1600/indah%2Bkusuma%2Bhot%2Bdoctor%2Bindonesia.jpg",
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
                        text = "${vet.rangeLocation} km",
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
                        text = "${vet.startWorkingHours} - ${vet.endWorkingHours}",
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
                    text = vet.name,
                    color = Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )

                Text(
                    text = vet.workPlace,
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
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
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
                    vet.studies.forEach {
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
                                text = it,
                                color = Black,
                                textAlign = TextAlign.Start,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = modifier
                .padding(start = 22.dp, end = 22.dp, bottom = 15.dp)
                .background(White, RoundedCornerShape(10.dp))
                .border(2.dp, DarkGreen, RoundedCornerShape(10.dp))
                .height(700.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                GoogleMap(
                    properties = mapProperties,
                    cameraPositionState = cameraPositionState,
                    uiSettings = uiSettings,
                    modifier = modifier.fillMaxHeight()
                )

                Text(
                    text = vet.location,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = modifier
                        .background(White, RoundedCornerShape(15.dp, 15.dp))
                        .padding(15.dp)
                        .align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VetProfilePreview() {
    PawraTheme {
        val vetData = VetData(
            "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "drh. Humberto Chavez",
            2.3,
            8,
            9,
            15,
            "Klinik Hewan Purnama",
            "Female",
            75,
            "White, Gold",
            "Jl. Brigjen Sudirman No.5, Purbayan, Kec. Mergangsan, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55131",
            220.2209029,
            2909090.3209302,
            listOf("Doctor of Veterinary Medicine (DVM), University of Veterinary Sciences, 2007", "Bachelor of Science in Animal Science, State University, 2003.")
        )
        VetProfile(
            vet = vetData,
            viewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            navController = rememberNavController()
        )
    }
}