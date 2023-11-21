package id.pawra.ui.components.profile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Poppins

@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier
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
            text = "Profile Page",
            fontSize = 17.sp,
            color = DarkGreen,
            fontFamily = Poppins,
            modifier = modifier.weight(1f),
        )

        IconButton(
            onClick = { /* Handle setting click */ },
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                tint = colorResource(id = R.color.black)
            )
        }
    }
}
