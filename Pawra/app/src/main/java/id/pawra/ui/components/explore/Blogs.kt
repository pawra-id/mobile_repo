package id.pawra.ui.components.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.MobileGray
import id.pawra.ui.theme.PawraTheme

@Composable
fun Blogs(
    modifier: Modifier = Modifier
) {

    val title = "Here is 5 possible reasons your dog become aggressive."
    val date = "23 February 2023"

    Column(
        modifier = modifier
            .padding(
                top = 10.dp,
                bottom = 10.dp
            )
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable { }
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Joko_Widodo_2019_official_portrait.jpg/639px-Joko_Widodo_2019_official_portrait.jpg",
                contentDescription = "Admin Profile Picture",
                modifier = modifier
                    .size(28.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Text(
                color = MobileGray,
                fontSize = 13.sp,
                text = "by",
                modifier = modifier.padding(start = 10.dp)
            )
            Text(
                text = "Admin",
                color = MobileGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(start = 5.dp)
            )
        }

        AsyncImage(
            model = "https://media.istockphoto.com/id/1210830302/photo/agressive-dogs-dog-attack-dog-fight.webp?b=1&s=170667a&w=0&k=20&c=ZlCp4kZ2AJlilf4NM2fdr5rI5pjFNkKb7YSehrHYfjs=",
            contentDescription = title,
            modifier = modifier.padding(top = 10.dp)
                .height(185.dp)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = title,
            color = Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(top = 10.dp)
        )

        Text(
            text = date,
            color = MobileGray,
            fontSize = 13.sp,
            modifier = modifier.padding(top = 5.dp).align(Alignment.End)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BlogsPreview() {
    PawraTheme {
        Blogs()
    }
}