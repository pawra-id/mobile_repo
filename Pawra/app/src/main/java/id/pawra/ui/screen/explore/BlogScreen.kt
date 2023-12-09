package id.pawra.ui.screen.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.components.blogs.BlogDetail
import id.pawra.ui.components.blogs.BlogDetailTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun BlogScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column {
        BlogDetailTopBar(navController = rememberNavController())

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            BlogDetail()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BlogScreenPreview() {
    PawraTheme {
        BlogScreen(navController = rememberNavController())
    }
}
