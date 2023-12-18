package id.pawra.ui.components.addactivities

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.ChipsModel
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.screen.pet.activities.AddActivityFormEvent
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
)
@Composable
fun AddActivitiesForm (
    modifier: Modifier = Modifier,
    petId: Int,
    navController: NavController,
    activitiesViewModel: ActivitiesViewModel,
    petViewModel: PetViewModel,
    showDialog: (Boolean) -> Unit,
){
    val state = activitiesViewModel.stateAddActivity
    var listDog = listOf<MutableMap<*,*>>()

    LaunchedEffect(Unit){
        petViewModel.getListDogToAddDogForm(petId)
    }

    petViewModel.petListState.collectAsState(initial = emptyList()).value.let { dogList ->
        if (dogList.isNotEmpty()) {
            listDog = dogList
            LaunchedEffect(Unit){
                state.dog = dogList[0]["name"].toString()
                state.dogId = dogList[0]["id"] as Int
            }
        }
    }
    val chipDataSnapshotStateList = remember {
        activitiesViewModel.tagChip
    }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    var isExpanded by remember { mutableStateOf(false) }
    val selectedItems = remember {
        mutableStateListOf<String>()
    }

    Column{
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
                value = state.dog,
                onValueChange = {  },
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
                ),
                supportingText = {
                    if (state.dogError != null) {
                        Text(
                            text = state.dogError,
                            color = Red,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                },
                isError = state.dogError != null
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                listDog.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text= item["name"].toString(),
                                fontSize = 14.sp,
                            )},
                        onClick = {
                            state.dog = item["name"].toString()
                            state.dogId = item["id"] as Int
                            isExpanded = false
                            activitiesViewModel.onEventAddActivity(AddActivityFormEvent.DogChanged(item["name"].toString()))
                        }
                    )
                }
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
            value = state.activity,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { activitiesViewModel.onEventAddActivity(AddActivityFormEvent.ActivityChanged(it)) },
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
            ),
            supportingText = {
                if (state.activityError != null) {
                    Text(
                        text = state.activityError,
                        color = Red,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            },
            isError = state.activityError != null
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

        FlowRow(
            modifier = modifier
                .drawWithContent {
                    drawContent()
                    drawLine(
                        DarkGreen,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx(),
                        cap = StrokeCap.Round,
                    )
                },
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            chipDataSnapshotStateList.forEach { item ->
                if (item.text.isNotEmpty()){
                    TagsChip(item){
                        chipDataSnapshotStateList.remove(item)
                        selectedItems.remove(item.text)
                        activitiesViewModel.onEventAddActivity(AddActivityFormEvent.TagsChanged(state.tags))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .height(54.dp)
                    .widthIn(min = 80.dp)
                    .weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                val bringIntoViewRequester = remember { BringIntoViewRequester() }
                val coroutineScope = rememberCoroutineScope()

                Column {
                    BasicTextField(
                        value = state.tags,
                        onValueChange = {
                            activitiesViewModel.onEventAddActivity(AddActivityFormEvent.TagsChanged(it)) },
                        singleLine = false,

                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .bringIntoViewRequester(bringIntoViewRequester)
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    coroutineScope.launch {
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        textStyle = TextStyle.Default.copy(
                            fontSize = 14.sp,
                            color = Gray,
                            fontFamily = Poppins,
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (state.tags.isNotEmpty()) {
                                    keyboardController?.hide()
                                    val chip = ChipData(
                                        text = state.tags
                                    )
                                    if (!chipDataSnapshotStateList.contains(chip)){
                                        selectedItems.add(state.tags)
                                        chipDataSnapshotStateList.add(chip)
                                        activitiesViewModel.onEventAddActivity(AddActivityFormEvent.TagsChanged(""))
                                    }
                                    state.tags = ""
                                    keyboardController?.show()
                                }
                            }
                        )
                    )
                }
            }
        }

        Row(
            modifier = modifier.padding(vertical = 10.dp)
        ) {
            if (state.tagsError != null) {
                Text(
                    text = state.tagsError,
                    color = Red,
                    fontSize = 14.sp
                )
            }
        }

        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            inputList.forEach { item ->

                val isSelected = selectedItems.contains(item.name)

                InputChip(
                    selected = isSelected,
                    onClick = {
                        val chip = ChipData(
                            text = item.name
                        )
                        if (isSelected) {
                            chipDataSnapshotStateList.remove(chip)
                            selectedItems.remove(item.name)
                            activitiesViewModel.onEventAddActivity(AddActivityFormEvent.TagsChanged(state.tags))
                        } else {
                            chipDataSnapshotStateList.add(chip)
                            selectedItems.add(item.name)
                            activitiesViewModel.onEventAddActivity(AddActivityFormEvent.TagsChanged(state.tags))
                        }
                    },
                    label = { Text(
                        text = item.name,
                        color = if (isSelected) DarkGreen else Gray
                        )
                    },
                    colors = InputChipDefaults.inputChipColors(
                        selectedContainerColor = LightGreen,
                        selectedLabelColor = DarkGreen
                    ),
                    trailingIcon = {
                        if (isSelected) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                activitiesViewModel.onEventAddActivity(AddActivityFormEvent.Submit)
                showDialog(activitiesViewModel.showDialog)
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
        AddActivitiesForm(
            navController = rememberNavController(),
            petId = 0,
            activitiesViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            petViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            showDialog = {}
        )
    }
}
