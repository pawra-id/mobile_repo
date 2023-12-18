package id.pawra.ui.components.vets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun VetsTopBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .background(White)
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

        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nearby Vets",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = DarkGreen,
                modifier = modifier
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VetsTopBarPreview() {
    PawraTheme {
        VetsTopBar(
            navController = rememberNavController()
        )
    }
}