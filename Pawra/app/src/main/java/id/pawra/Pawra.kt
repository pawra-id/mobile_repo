package id.pawra

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.pawra.ui.screen.auth.SignUpScreen
import id.pawra.ui.theme.PawraTheme

@Composable
fun Pawra(
    modifier: Modifier = Modifier,
) {
    SignUpScreen()
}

@Preview(showBackground = true)
@Composable
fun PawraAppPreview() {
    PawraTheme {
        Pawra()
    }
}