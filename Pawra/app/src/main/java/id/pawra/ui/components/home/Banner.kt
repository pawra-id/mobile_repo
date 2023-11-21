package id.pawra.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun Banner(
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val bannerWidth = screenWidth - 44.dp

    Row(
        modifier = modifier
            .horizontalScroll( rememberScrollState(),true)
            .fillMaxWidth()
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = modifier
                .height(130.dp)
                .width(bannerWidth)
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
                    text = "Check your dog now",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.dark_green),
                )
                Text(
                    text = "Don't waste any more time",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = colorResource(id = R.color.dark_green),
                    lineHeight = 15.sp
                )

            }
            Button(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .height(30.dp)
                    .width(65.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_green)
                ),
            ) {
                Text(
                    text = "Go",
                    fontFamily = Poppins,
                    fontSize = 13.sp
                )
            }

//          TODO: change image to proper image

//            Image(
//                painter = painterResource(id = R.drawable.banner1),
//                contentDescription = "Banner 1",
//                contentScale = ContentScale.Fit,
//                modifier = modifier
//                    .size(180.dp).fillMaxHeight()
//                    .align(Alignment.BottomEnd)
//            )
        }

        Box(
            modifier = modifier
                .height(130.dp)
                .width(bannerWidth)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.linear1),
                            colorResource(id = R.color.linear2)
                        )
                    )
                ),
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Find out more about others",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.white),
                )
                Text(
                    text = "Check these shared mental health result",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    color = colorResource(id = R.color.white),
                    lineHeight = 15.sp
                )
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .height(30.dp)
                    .width(80.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.white)
                ),
            ) {
                Text(
                    text = "Explore",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.linear1),
                                colorResource(id = R.color.linear2)
                            ),
                        )
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BannerPreview() {
    PawraTheme {
        Banner()
    }
}