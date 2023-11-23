package id.pawra.ui.components.general

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    topBar: @Composable (() -> Unit),
    sheetContent: @Composable  (() -> Unit),
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = screenHeight - 180.dp,
//        sheetPeekHeight = screenHeight - ((screenHeight / 2) / 2),
        sheetContainerColor = White,
        sheetShape = RoundedCornerShape(40.dp, 40.dp),
        sheetContent = {
            sheetContent()
        },
        sheetDragHandle = {
            DragHandle()
        }
    ) {
        topBar()
    }
}

@Composable
fun DragHandle(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraLarge,
) {
    Surface(
        modifier = modifier
            .padding(vertical = 22.dp),
        color = LightGray,
        shape = shape
    ) {
        Box(
            Modifier
                .size(
                    width = 70.dp,
                    height = 6.dp
                )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BottomSheetPreview() {
    PawraTheme {
        BottomSheet(
            {},
            {}
        )
    }
}