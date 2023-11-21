package id.pawra.ui.components.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.MobileGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun MenuSearch(
    modifier: Modifier = Modifier
) {
    var activeMenu by remember { mutableStateOf(Menu.MentalHealth.name) }
    val query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(if (activeMenu == Menu.MentalHealth.name) LightGreen else White)
                    .border(
                        2.dp, if (activeMenu == Menu.MentalHealth.name) LightGreen else LightGray,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 5.dp, horizontal = 25.dp)
                    .clickable {
                        activeMenu = Menu.MentalHealth.name
                    }
            ) {
                Text(
                    text = "Mental health",
                    fontSize = 13.sp,
                    color = if (activeMenu == Menu.MentalHealth.name) DarkGreen else LightGray,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Box(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(if (activeMenu == Menu.Blogs.name) LightGreen else White)
                    .border(
                        2.dp, if (activeMenu == Menu.Blogs.name) LightGreen else LightGray,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 5.dp, horizontal = 25.dp)
                    .clickable {
                        activeMenu = Menu.Blogs.name
                    }
            ) {
                Text(
                    text = "Blogs",
                    fontSize = 13.sp,
                    color = if (activeMenu == Menu.Blogs.name) DarkGreen else LightGray,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        SearchBar(
            query = query,
            onQueryChange = {  },
            onSearch = {},
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MobileGray
                )
            },
            placeholder = {
                Text(
                    "Search",
                    color = MobileGray
                )
            },
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {}

        ListExplore("",
            "Dianna",
            "Female",
            activeMenu
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MenuSearchPreview() {
    PawraTheme {
        MenuSearch()
    }
}