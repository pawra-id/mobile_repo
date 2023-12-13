package id.pawra.ui.components.blogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.BlogsData
import id.pawra.data.remote.response.BlogsResponseItem
import id.pawra.ui.screen.explore.BlogsViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme

@Composable
fun BlogDetail(
    blogs: BlogsResponseItem,
    blogsViewModel: BlogsViewModel,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier
            .padding(bottom = 10.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable { }
    ) {
        AsyncImage(
            model = blogs.image,
            contentDescription = blogs.title,
            modifier = modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .height(185.dp)
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                color = Gray,
                fontSize = 13.sp,
                text = "by"
            )
            Text(
                text = "Admin",
                color = Gray,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(start = 5.dp)
            )
        }

        Text(
            text = blogs.createdAt ?: "",
            color = Gray,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(top = 5.dp, bottom = 10.dp)
        )

        Text(
            text = blogs.title ?: "",
            color = Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(top = 10.dp, bottom = 8.dp),
            lineHeight = 30.sp
        )

        Text(
            text = blogs.content ?: "",
            color = Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = modifier.padding(top = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BlogDetailPreview() {
    PawraTheme {
        val blogsData = BlogsData(
            "https://media.istockphoto.com/id/1210830302/photo/agressive-dogs-dog-attack-dog-fight.webp?b=1&s=170667a&w=0&k=20&c=ZlCp4kZ2AJlilf4NM2fdr5rI5pjFNkKb7YSehrHYfjs=",
            "23 February 2023",
            "Here is 5 possible reasons your dog become aggressive.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        )
        BlogDetail(
            blogs = BlogsResponseItem(id=0),
            blogsViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}