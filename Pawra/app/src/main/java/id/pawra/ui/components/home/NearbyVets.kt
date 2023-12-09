package id.pawra.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.components.explore.Menu
import id.pawra.ui.components.profile.ProfileImage
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun NearbyVets(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val image = "https://2.bp.blogspot.com/-_sOpXiMO0m4/ViVULhN611I/AAAAAAAAMCk/LbqKTS7T9Fw/s1600/indah%2Bkusuma%2Bhot%2Bdoctor%2Bindonesia.jpg"
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var activeVet by remember { mutableStateOf(Menu.MentalHealth.name) }

    Column(
        modifier = modifier
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nearby Vets",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                modifier = modifier.weight(1f)
            )

            Text(
                text = "more",
                color = Gray, fontFamily = Poppins, fontSize = 13.sp,
                modifier = modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        navController.navigate(Screen.Vets.route)
                    }
            )
        }
        Row(
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 0.dp)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(60.dp)
                    .clip(shape = CircleShape)
                    .border(5.dp, LightGreen, shape = CircleShape)
                    .clickable { }
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(50.dp)
                ) {
                    AsyncImage(
                        model = image,
                        placeholder = painterResource(id = R.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                            .size(50.dp)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(60.dp)
                    .clip(shape = CircleShape)
                    .border((1.5).dp, LightGreen, shape = CircleShape)
                    .clickable { }
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(50.dp)
                ) {
                    AsyncImage(
                        model = "",
                        placeholder = painterResource(id = R.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                            .size(50.dp)
                    )
                }
            }
        }

        Box(
            modifier = modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .width(screenWidth)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = DisabledGreen),
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "drh. Heinrich Himmler",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGreen
                )
                Text(
                    text = "Jl. Roro Jgn, Ds. Baru, Suramadu, Jogjakarta",
                    fontSize = 11.sp,
                    fontStyle = FontStyle.Italic,
                    color = DarkGreen
                )
                Text(
                    text = "14 years of experience as a senior veterinarian at Jogja 2 Vet Clinic and love dogs",
                    fontSize = 13.sp,
                    color = DarkGreen,
                    modifier = modifier.padding(top = 10.dp)
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = modifier
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    ),
                ) {
                    Text(
                        text = "Consult",
                        fontFamily = Poppins,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NearbyVetsPreview() {
    PawraTheme {
        NearbyVets(
            navController = rememberNavController()
        )
    }
}