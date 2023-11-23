package id.pawra.ui.screen.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import id.pawra.ui.components.explore.ExploreTopBar
import id.pawra.ui.components.explore.MenuSearch

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ExploreTopBar()
        MenuSearch()
    }
}