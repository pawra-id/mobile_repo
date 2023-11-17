package id.pawra.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.home.Welcome
import id.pawra.ui.theme.PawraTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Welcome(image = "a")
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    PawraTheme {
        HomeScreen(
            navHomeController = rememberNavController()
        )
    }
}