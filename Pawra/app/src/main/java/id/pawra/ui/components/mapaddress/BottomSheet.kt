package id.pawra.ui.components.mapaddress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    content: @Composable (() -> Unit),
    sheetContent: @Composable (() -> Unit),
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Expanded)
    )

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val halfScreenHeight = (screenHeight / 2)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
//        sheetPeekHeight = halfScreenHeight - (halfScreenHeight / 2),
        sheetContainerColor = White,
        sheetShape = RoundedCornerShape(40.dp, 40.dp),
        sheetContent = {
            sheetContent()
        },
        sheetDragHandle = {
            DragHandle()
        }
    ) {
        content()
    }
}

@Composable
fun DragHandle(
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
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