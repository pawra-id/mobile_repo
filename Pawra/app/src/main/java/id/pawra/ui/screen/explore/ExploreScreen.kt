package id.pawra.ui.screen.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import id.pawra.ui.components.explore.ExploreTopBar
import id.pawra.ui.components.explore.ListExplore
import id.pawra.ui.components.explore.Menu
import id.pawra.ui.components.explore.MenuSearch

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ExploreTopBar()
        MenuSearch()
    }
}