package id.pawra.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun AddButton(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp, end = 5.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ){
        ExtendedFloatingActionButton(
            onClick = {
                navController.navigate(Screen.PetActivitiesAdd.route)
                      },
            shape = CircleShape,
            containerColor = LightGreen,
            contentColor = DarkGreen,
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Add Activities",
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = "Activities",
                fontFamily = Poppins,
                fontSize = 13.sp,
                modifier = modifier.padding(start = 5.dp, end = 5.dp).align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddButtonPreview() {
    PawraTheme {
        AddButton(
            navController = rememberNavController()
        )
    }
}