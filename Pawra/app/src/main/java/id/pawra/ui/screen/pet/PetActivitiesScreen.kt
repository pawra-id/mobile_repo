package id.pawra.ui.screen.pet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import id.pawra.ui.components.general.BottomSheet
import id.pawra.ui.components.petactivities.PetActivitiesBottomSheet
import id.pawra.ui.components.petactivities.PetActivitiesTopBar

@Composable
fun PetActivitiesScreen(
    modifier: Modifier = Modifier,
    navPetController: NavController
) {

    PetActivitiesBottomSheet(
        modifier
    )

}