package id.pawra.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White
import id.pawra.utils.DateConverter

@Composable
fun Activities(
    modifier: Modifier = Modifier,
    data: ActivitiesResponseItem,
    navController: NavController,
) {
    Row(
        modifier = modifier
            .height(120.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(White)
            .clickable {
                navController.navigate(Screen.PetActivitiesPrev.createRoute(data.id))
            }
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = data.dog?.image,
            contentDescription = "",
            modifier = modifier
                .size(90.dp)
                .clip(RoundedCornerShape(15.dp))
                .border(1.dp, LightGray, RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
        ) {
            Text(
                text = DateConverter.convertStringToDate(data.createdAt ?: ""),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = LightGray
            )

            Text(
                text = data.description ?: "",
                fontSize = 13.sp,
                color = Black,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.weight(1f)
            )

            LazyRow(
                modifier = modifier.padding(top = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Bottom
                ) {
                items(data.tags!!, key = { it.id }) { tag ->
                    Text(
                        text = tag.name ?: "",
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

@Composable
@Preview(showBackground = true)
fun ActivitiesPreview() {
    PawraTheme {
        Activities(
            data = ActivitiesResponseItem(id = 0),
            navController = rememberNavController()
        )
    }
}