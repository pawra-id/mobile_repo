package id.pawra.ui.screen.pet

import androidx.compose.foundation.layout.Arrangement
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
import id.pawra.ui.components.mentalhealth.Analyze
import id.pawra.ui.components.mentalhealth.AnalyzeHistory
import id.pawra.ui.components.mentalhealth.MentalHealthTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetMentalHealthScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MentalHealthTopBar()
        Analyze()
        AnalyzeHistory()
    }
}

@Composable
@Preview(showBackground = true)
fun PetMentalHealthScreenPreview() {
    PawraTheme {
        PetMentalHealthScreen(navController = rememberNavController())
    }
}