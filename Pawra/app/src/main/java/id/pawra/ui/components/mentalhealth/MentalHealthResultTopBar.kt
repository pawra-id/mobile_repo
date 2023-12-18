package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import id.pawra.ui.screen.pet.mentalhealth.AnalysisViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red


@Composable
fun MentalHealthResultTopBar (
    modifier: Modifier = Modifier,
    navController: NavController,
    analysisViewModel: AnalysisViewModel,
    isShared : Boolean,
    analysisId: Int,
    isSharedScreen: Boolean,
    setShowDialog: (Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 15.dp),
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = modifier
                .background(DisabledGreen, CircleShape)
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back",
                tint = DarkGreen,
                modifier = modifier.size(18.dp)
            )
        }

        if (!isSharedScreen) {
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                IconButton(
                    modifier = modifier
                        .size(32.dp),
                    onClick = {
                        if (isShared) {
                            analysisViewModel.unshareAnalysis(analysisId)
                        } else {
                            analysisViewModel.shareAnalysis(analysisId)
                        }
                        setShowDialog(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = if (isShared) DarkGreen else Red,
                        modifier = modifier.size(25.dp),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MentalHealthResultTopBarPreview() {
    PawraTheme {
        MentalHealthResultTopBar(
            navController = rememberNavController(),
            analysisViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            analysisId = 0,
            setShowDialog = {},
            isShared = false,
            isSharedScreen = false
        )
    }
}