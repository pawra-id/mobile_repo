package id.pawra.ui.components.addactivities

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.local.preference.ChipsModel
import id.pawra.ui.navigation.Screen
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivitiesForm (
    modifier: Modifier = Modifier,
    navController: NavController
){
    Column{
        var isExpanded by remember { mutableStateOf(false) }
        var selectdog by remember { mutableStateOf("Max") }

        var activity by rememberSaveable { mutableStateOf("") }

        val selectedItems = mutableStateListOf<String>()
        var tagsInput by remember { mutableStateOf("") }
        var selectedTags by remember { mutableStateOf(listOf<String>()) }


        Text(
            text = stringResource(R.string.select_dog),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            OutlinedTextField(
                value = selectdog,
                onValueChange = { selectdog = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    color = Gray,
                    fontFamily = Poppins
                )
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {Text(
                        text="Max",
                        fontSize = 14.sp,
                    )},
                    onClick = {
                        selectdog = "Max"
                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = {Text(
                        text="Diana",
                        fontSize = 14.sp,
                    )},
                    onClick = {
                        selectdog = "Diana"
                        isExpanded = false
                    }
                )
            }
        }

        Text(
            text = stringResource(R.string.activity),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )

        OutlinedTextField(
            value = activity,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { activity = it },
            singleLine = false,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = Gray,
                fontFamily = Poppins
            )
        )

        Text(
            text = stringResource(R.string.tags),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )

        val inputList = listOf(
            ChipsModel(
                name = "Poop",
                textExpanded = "poop",
            ),
            ChipsModel(
                name = "Sad",
                textExpanded = "sad",
            )
        )

        OutlinedTextField(
            value = tagsInput,
            onValueChange = { tagsInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            singleLine = false,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = Gray,
                fontFamily = Poppins,
            )
        )

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
        ) {
            items(inputList) { item ->
                val isSelected = selectedItems.contains(item.name)
                Spacer(modifier = Modifier.padding(5.dp))
                InputChip(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) {
                            selectedItems.add(item.name)
                        } else {
                            selectedItems.remove(item.name)
                        }
                        selectedTags = selectedItems.toList()
                        tagsInput = selectedTags.joinToString()
                    },
                    label = { Text(
                        text = item.name,
                        color = if (isSelected) DarkGreen else Gray
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                navController.navigate(Screen.PetActivitiesAdd.route)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.submit),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 7.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun AddActivitiesFormPreview() {
    PawraTheme {
        AddActivitiesForm(navController = rememberNavController())
    }
}
