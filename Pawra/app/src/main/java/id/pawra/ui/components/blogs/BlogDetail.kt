package id.pawra.ui.components.blogs

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
import id.pawra.data.remote.response.BlogsResponseItem
import id.pawra.ui.screen.explore.BlogsViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.utils.DateConverter

@Composable
fun BlogDetail(
    blogs: BlogsResponseItem,
    blogsViewModel: BlogsViewModel,
    modifier: Modifier = Modifier,
){
    
    Column(
        modifier = modifier
            .padding(bottom = 10.dp)
            .clip(shape = RoundedCornerShape(15.dp))
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
                text = blogs.author?.username ?: "Unknown",
                color = Gray,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(start = 5.dp)
            )
        }

        Text(
            text = DateConverter.convertStringToDate(blogs.createdAt ?: ""),
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
        BlogDetail(
            blogs = BlogsResponseItem(id=0),
            blogsViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}