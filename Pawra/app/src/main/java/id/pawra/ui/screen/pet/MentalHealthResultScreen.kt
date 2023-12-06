package id.pawra.ui.screen.pet

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
import id.pawra.data.local.preference.MentalHealthData
import id.pawra.ui.components.mentalhealth.MentalHealthAction
import id.pawra.ui.components.mentalhealth.MentalHealthResult
import id.pawra.ui.components.mentalhealth.MentalHealthResultGraph
import id.pawra.ui.components.mentalhealth.MentalHealthResultTitle
import id.pawra.ui.components.mentalhealth.MentalHealthResultTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun MentalHealthResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Column {
        MentalHealthResultTopBar(navController = rememberNavController())

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            MentalHealthResultTitle()
            MentalHealthResultGraph()
            MentalHealthResult()
        }

        val mentalHealthData = MentalHealthData(
            "Max",
            70f,
            "There is 70% chance that your dog has depression",
            "Some of the symptoms that the machine catch are sudden change on behavior, eat less food, and aggressiveness toward other animals",
            listOf(
                "Take your pet for a walk daily.",
                "Spend quality time playing with your pet.",
                "Consider consulting a veterinarian for professional advice."
            )
        )

        MentalHealthAction(mentalHealthData = mentalHealthData)
    }
}

@Composable
@Preview(showBackground = true)
fun MentalHealthResultScreenPreview() {
    PawraTheme {
        MentalHealthResultScreen(
            navController = rememberNavController()
        )
    }
}