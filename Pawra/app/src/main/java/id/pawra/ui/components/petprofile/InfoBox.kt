package id.pawra.ui.components.petprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DisabledOrange
import id.pawra.ui.theme.Orange
import id.pawra.ui.theme.Poppins

@Composable
fun InfoBox(title: String, value: String) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .width(120.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(DisabledOrange),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(bottom = 4.dp),
                color = Orange
            )
            Text(
                text = value,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Black
            )
        }
    }
}