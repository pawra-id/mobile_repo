package id.pawra.ui.components.addactivities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import id.pawra.R
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun AddActivitiesForm (
    modifier: Modifier = Modifier,
    navController: NavController
){
    Column{
        var isExpanded by remember { mutableStateOf(false) }
        var select_dog by remember { mutableStateOf("Max") }

        var activity by rememberSaveable { mutableStateOf("") }

        Text(
            text = stringResource(R.string.name),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        OutlinedTextField(
            value = select_dog,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { select_dog = it },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = Gray,
                fontFamily = Poppins
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddActivitiesFormPreview() {
    PawraTheme {
        AddActivitiesTitle()
    }
}
