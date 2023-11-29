package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DarkPink
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledPink
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme

@Composable
fun AnalyzeHistory(
    modifier: Modifier = Modifier
) {
    val name = "Max"
    val gender = "Male"

    Column(
        modifier = modifier
            .padding(horizontal = 22.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "History",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Black,
            modifier = modifier
                .padding(bottom = 10.dp)
        )

        Row(
            modifier = modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp
                )
                .height(90.dp)
                .clickable { }
        ) {
            Box(
                modifier = modifier
            ) {
                AsyncImage(
                    model = "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
                    contentDescription = "",
                    modifier = modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = modifier
                        .padding(5.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = gender,
                        fontSize = 11.sp,
                        color = if (gender == "Male") DarkBlue else DarkPink,
                        modifier = modifier
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(
                                color = if (gender == "Male") DisabledBlue else DisabledPink
                            )
                            .padding(vertical = 2.dp, horizontal = 10.dp)
                    )
                }

            }

            Box {
                Icon(
                    Icons.Filled.Share,
                    "Share Analyze",
                    modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp),
                    Black
                )

                Column(
                    modifier = modifier.padding(start = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )

                    Text(
                        text = "15:24, 12 May 2023",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LightGray
                    )

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec porttitor quis nisi a fringilla. Etiam tempor orci in nisl consectetur, ac venenatis eros eleifend. Morbi massa odio, rhoncus quis iaculis ullamcorper, vestibulum in enim.",
                        fontSize = 13.sp,
                        color = Black,
                        lineHeight = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.weight(1f)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),

                        ) {
                        Text(
                            text = "56% Depression",
                            fontSize = 10.sp,
                            color = DarkGreen,
                            modifier = modifier
                                .clip(shape = RoundedCornerShape(15.dp))
                                .background(color = DisabledGreen)
                                .padding(vertical = 2.dp, horizontal = 10.dp),
                        )
                    }
                }
            }

        }

    }
}

@Composable
@Preview(showBackground = true)
fun AnalyzeHistoryPreview() {
    PawraTheme {
        AnalyzeHistory()
    }
}