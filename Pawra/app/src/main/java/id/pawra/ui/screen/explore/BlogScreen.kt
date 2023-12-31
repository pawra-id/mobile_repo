package id.pawra.ui.screen.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.blogs.BlogDetail
import id.pawra.ui.components.blogs.BlogDetailTopBar
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.theme.PawraTheme

@Composable
fun BlogScreen(
    modifier: Modifier = Modifier,
    blogsViewModel: BlogsViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    blogsId: Int,
    navController: NavController
) {
    blogsViewModel.getDetailBlogs(blogsId)
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

    Column {
        BlogDetailTopBar(navController = navController)

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            blogsViewModel.blogsDetailState.collectAsState().value.let { blogsDetailState ->
                when (blogsDetailState) {
                    is UiState.Success -> {
                        isLoading = false
                        BlogDetail(
                            blogs = blogsDetailState.data,
                            blogsViewModel = blogsViewModel
                        )
                    }
                    is UiState.Loading -> {
                        isLoading = true
                    }

                    is UiState.Error -> {
                        if (showDialog) {
                            isLoading = false
                            ResultDialog(
                                success = false,
                                message = blogsDetailState.errorMessage,
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
}

@Composable
@Preview(showBackground = true)
fun BlogScreenPreview() {
    PawraTheme {
        BlogScreen(
            navController = rememberNavController(),
            blogsId = 0,
            blogsViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            )
        )
    }
}
