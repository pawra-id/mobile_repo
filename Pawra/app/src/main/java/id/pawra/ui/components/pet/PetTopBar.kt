package id.pawra.ui.components.pet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun PetTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 20.dp,
                bottom = 20.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dogs Page",
            fontSize = 17.sp,
            color = DarkGreen,
            fontFamily = Poppins,
            modifier = modifier.weight(1f),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PetTopBarPreview() {
    PawraTheme {
        PetTopBar()
    }
}