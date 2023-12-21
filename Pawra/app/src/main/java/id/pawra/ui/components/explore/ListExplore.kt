package id.pawra.ui.components.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.theme.PawraTheme

@Composable
fun ListExplore(
    activeMenu: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    Column(
        modifier = modifier.padding(top = 10.dp)
    ) {
        if (activeMenu == Menu.MentalHealth.name) {
            SharedMental(
                navController = navController,
                analysisViewModel = viewModel(
                    factory = ViewModelFactory(LocalContext.current)
                )
            )
        } else {
            Blogs(
                navController = navController,
                blogsViewModel = viewModel(
                    factory = ViewModelFactory(LocalContext.current)
                )
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ListExplorePreview() {
    PawraTheme {
        ListExplore(
            "",
            navController = rememberNavController()
        )
    }
}