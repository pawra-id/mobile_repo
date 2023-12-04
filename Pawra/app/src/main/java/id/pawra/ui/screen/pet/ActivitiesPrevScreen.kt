package id.pawra.ui.screen.pet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.ui.components.addactivities.ActivitiesPrevInfo
import id.pawra.ui.components.addactivities.ActivitiesPrevTitle
import id.pawra.ui.components.addactivities.ActivitiesPrevTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun ActivitiesPrevScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    Column {
        ActivitiesPrevTopBar(navController = rememberNavController())

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            ActivitiesPrevTitle()

            Spacer(modifier = Modifier.height(15.dp))

            ActivitiesPrevInfo(
                pet = PetResponseItem(id=1),
                viewModel = viewModel(
                    factory = ViewModelFactory(LocalContext.current)
            ))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ActivitiesPrevScreenPreview() {
    PawraTheme {
        ActivitiesPrevScreen(
            navController = rememberNavController()
        )
    }
}