package id.pawra.ui.components.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.theme.PawraTheme

@Composable
fun ListExplore(
    activeMenu: String,
    modifier: Modifier = Modifier,
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    activitiesViewModel.getActivities()

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
        modifier = modifier.padding(top = 10.dp)
    ) {
        if (activeMenu == Menu.MentalHealth.name) {
            (0 until 3).forEach { _ ->
                SharedMental()
            }
        } else {
            (0 until 3).forEach { _ ->
                Blogs()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ListExplorePreview() {
    PawraTheme {
        ListExplore(
            ""
        )
    }
}