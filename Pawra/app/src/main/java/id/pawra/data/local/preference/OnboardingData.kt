package id.pawra.data.local.preference

import androidx.compose.ui.graphics.Color
import id.pawra.ui.theme.LightGreen

data class OnBoardingData(
    val image: Int,
    val title: String,
    val subtitle: String,
    val desc: String,
    val backgroundColor:Color,
    val mainColor: Color = LightGreen
)