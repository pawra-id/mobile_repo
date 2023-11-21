package id.pawra.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.components.profile.ProfileImage
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.MobileGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun NearbyVets(
    modifier: Modifier = Modifier
) {
    val image = "https://2.bp.blogspot.com/-_sOpXiMO0m4/ViVULhN611I/AAAAAAAAMCk/LbqKTS7T9Fw/s1600/indah%2Bkusuma%2Bhot%2Bdoctor%2Bindonesia.jpg"
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

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
                color = colorResource(id = R.color.black),
                modifier = modifier.weight(1f)
            )

            Text(
                text = "more",
                color = MobileGray, fontFamily = Poppins, fontSize = 13.sp,
                modifier = modifier
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable{

                }
            )
        }
        Row(
            modifier = modifier.padding(vertical = 10.dp, horizontal = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .clip(shape = CircleShape)
                    .clickable {  }
            ) {
                Image(
                    painterResource(id = R.drawable.background_profileimage),
                    modifier = Modifier
                        .size(60.dp),
                    contentDescription = "Background Profile Image",
                )
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
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(id = R.drawable.background_profileimage),
                    modifier = Modifier
                        .size(60.dp),
                    contentDescription = "Background Profile Image",
                )
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
                .background(color = colorResource(id = R.color.disabled_green)),
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
                        containerColor = colorResource(id = R.color.dark_green)
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
        NearbyVets()
    }
}