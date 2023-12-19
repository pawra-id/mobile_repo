package id.pawra.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.common.UiState
import id.pawra.ui.components.home.Activities
import id.pawra.ui.components.home.Banner
import id.pawra.ui.components.home.ListDog
import id.pawra.ui.components.home.NearbyVets
import id.pawra.ui.components.home.Welcome
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.screen.vet.VetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHomeController: NavController,
    navController: NavController,
    viewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    activitiesViewModel: ActivitiesViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    vetViewModel: VetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    viewModel.getSession()
    LaunchedEffect(Unit) {
        activitiesViewModel.getActivities()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Row {
            viewModel.sessionState.collectAsState().value.let { userInfo ->
                Welcome(
                    image = userInfo.image,
                    name = userInfo.name
                )
            }
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            item { Banner(
                navHomeController = navHomeController
            ) }
            item { ListDog(
                navController = navController,
                viewModel = petViewModel
            ) }

            item { NearbyVets(
                navController = navController,
                viewModel = vetViewModel
            ) }

            item {
                Row(
                    modifier = modifier
                        .padding(start = 22.dp, end = 22.dp, top = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Last Activities",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Black,
                        modifier = modifier.weight(1f)
                    )
                }

                activitiesViewModel.activitiesState.collectAsState().value.let { activitiesState ->
                    when (activitiesState) {
                        is UiState.Success -> {
                            Column(
                                modifier = modifier.padding(horizontal = 22.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ){
                                for (item in activitiesState.data.take(10)) {
                                    key(item) {
                                        Activities(
                                            data = item,
                                            navController = navController,
                                        )
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    PawraTheme {
        HomeScreen(
            navHomeController = rememberNavController(),
            navController = rememberNavController()
        )
    }
}