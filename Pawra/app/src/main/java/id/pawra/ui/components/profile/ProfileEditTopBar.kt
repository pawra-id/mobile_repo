package id.pawra.ui.components.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import id.pawra.di.Injection
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun ProfileEditTopBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Row(
        modifier = modifier
            .padding(
                start = 22.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 17.sp,
            color = DarkGreen,
            fontFamily = Poppins,
            modifier = modifier.weight(1f),
        )

        IconButton(
            modifier = Modifier
                .width(90.dp),
            onClick = {
                navController.navigateUp()
            },
        ) {
            Text(
                text = "Cancel",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = (Gray)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileEditTopBarPreview() {
    PawraTheme {
        ProfileEditTopBar(
            navController = rememberNavController()
        )
    }
}