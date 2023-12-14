package id.pawra.ui.screen.pet.mentalhealth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.mentalhealth.Analyze
import id.pawra.ui.components.mentalhealth.AnalyzeHistory
import id.pawra.ui.components.mentalhealth.MentalHealthTopBar
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.PawraTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetMentalHealthScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    petId: Int,
    analysisViewModel: AnalysisViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    ),
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MentalHealthTopBar()
        Analyze()
        AnalyzeHistory(
            navController = navController,
            analysisViewModel = analysisViewModel,
            petViewModel = petViewModel,
            petId = petId
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun PetMentalHealthScreenPreview() {
    PawraTheme {
        PetMentalHealthScreen(
            navController = rememberNavController(),
            petId = 0
        )
    }
}