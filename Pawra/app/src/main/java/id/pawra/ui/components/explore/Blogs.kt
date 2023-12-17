package id.pawra.ui.components.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.general.SearchBar
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.explore.BlogsViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.utils.DateConverter

@Composable
fun Blogs(
    modifier: Modifier = Modifier,
    navController: NavController,
    blogsViewModel: BlogsViewModel
) {

    val query by remember { blogsViewModel.query }

    LaunchedEffect(Unit){
        blogsViewModel.getBlogs("")
    }

    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    if (isLoading) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingBox()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        SearchBar(
            query = query,
            onQueryChange = blogsViewModel::getBlogs,
            onSearch = {},
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Gray
                )
            },
            placeholder = {
                Text(
                    "Search",
                    color = Gray
                )
            },
            modifier = modifier
                .padding(top = 16.dp, bottom = 12.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {}

        blogsViewModel.blogsState.collectAsState().value.let { blogsState ->
            when (blogsState) {
                is UiState.Loading -> {
                    isLoading = true
                }

                is UiState.Success -> {
                    isLoading = false

                    LazyColumn(
                        modifier = modifier
                            .weight(1f)
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(blogsState.data, key = { it.id }) {data ->
                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(15.dp))
                                    .clickable {
                                        navController.navigate(Screen.BlogDetail.createRoute(data.id))
                                    }
                                    .padding(horizontal = 10.dp, vertical = 15.dp),
                            ){
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    AsyncImage(
                                        model = data.author?.image ?: "",
                                        contentDescription = "Admin Profile Picture",
                                        modifier = modifier
                                            .size(28.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop,
                                    )

                                    Text(
                                        color = Gray,
                                        fontSize = 13.sp,
                                        text = "by",
                                        modifier = modifier.padding(start = 10.dp)
                                    )
                                    Text(
                                        text = data.author?.username ?: "Unknown",
                                        color = Gray,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = modifier.padding(start = 5.dp)
                                    )
                                }

                                AsyncImage(
                                    model = data.image,
                                    contentDescription = data.title,
                                    modifier = modifier
                                        .padding(top = 10.dp)
                                        .height(185.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Crop,
                                )

                                Text(
                                    text = data.title ?: "",
                                    color = Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = modifier
                                        .padding(top = 10.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.Start)
                                )

                                Text(
                                    text = DateConverter.convertStringToDate(data.createdAt ?: ""),
                                    color = Gray,
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.End,
                                    modifier = modifier
                                        .padding(top = 5.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    if(showDialog) {
                        isLoading = false
                        ResultDialog(
                            success = false,
                            message = blogsState.errorMessage,
                            setShowDialog = {
                                showDialog = it
                            }
                        )
                    }
                }

                else -> {}

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BlogsPreview() {
    PawraTheme {
        Blogs(
            navController = rememberNavController(),
            blogsViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}