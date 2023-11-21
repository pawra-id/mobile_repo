package id.pawra.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun AddButton(
    navHomeController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp, end = 10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ){
        FloatingActionButton(
            onClick = { navHomeController.navigate(Screen.EditProfile.route) },
            shape = CircleShape,
            containerColor = LightGreen,
            contentColor = DarkGreen,
        ) {
            Icon(Icons.Filled.Edit, "Add Story")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddButtonPreview() {
    PawraTheme {
        AddButton(
            navHomeController = rememberNavController()
        )
    }
}