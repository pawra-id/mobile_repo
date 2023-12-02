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
import id.pawra.ui.components.addactivities.AddActivitiesTitle
import id.pawra.ui.components.addactivities.AddActivitiesTopBar
import id.pawra.ui.theme.PawraTheme

@Composable
fun ActivitiesAddScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column {
        AddActivitiesTopBar(navController = rememberNavController())

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            AddActivitiesTitle()

        }
    }
}

@Composable
@Preview(showBackground = true)
fun ActivitiesAddScreenPreview() {
    PawraTheme {
        ActivitiesAddScreen(
            navController = rememberNavController()
        )
    }
}