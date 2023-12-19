package id.pawra.ui.components.addpet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.ui.screen.pet.profile.DogAddFormEvent
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAdd(
    navController: NavController,
    modifier: Modifier = Modifier,
    showDialog: (Boolean) -> Unit,
    petViewModel: PetViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }

    val state = petViewModel.addDogValidationState
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(state.file.ifEmpty { R.drawable.ic_photo }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            petViewModel.onEventAddDog(DogAddFormEvent.DogImageChanged(it.toString()), context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Box {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(12.dp)
                        .border(2.dp, if (state.fileError != null) Red else DarkGreen, CircleShape)
                        .size(125.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .clickable { launcher.launch("image/*") }
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(36.dp)
                        .background(DisabledGreen, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit Profile Image",
                        tint = (DarkGreen)
                    )
                }
            }

            if (state.fileError != null) {
                Text(
                    text = state.fileError,
                    color = Red,
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = stringResource(R.string.name),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = state.name,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_dog_name),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogNameChanged(it), context) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                disabledContainerColor = LightGray,
                errorContainerColor = LightGray,
                cursorColor = Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.nameError != null) {
                    Text(
                        text = state.nameError,
                        color = Red
                    )
                }
            },
            isError = state.nameError != null
        )

        Text(
            text = stringResource(R.string.breed),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )


        OutlinedTextField(
            value = state.breed,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_dog_breed),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogBreedChanged(it), context) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                disabledContainerColor = LightGray,
                errorContainerColor = LightGray,
                cursorColor = Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.breedError != null) {
                    Text(
                        text = state.breedError,
                        color = Red
                    )
                }
            },
            isError = state.breedError != null
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.neutred),
                    modifier = modifier
                        .weight(1f)
                        .padding(top = 8.dp, bottom = 8.dp),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                )

                Switch(
                    checked = state.neutered,
                    onCheckedChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogNeuteredChanged(it), context) },
                    modifier = modifier
                        .weight(1f)
                        .padding(end = 8.dp, top = 8.dp, bottom = 8.dp),
                    enabled = true,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DarkGreen,
                        checkedTrackColor = DisabledGreen,
                        uncheckedThumbColor = DarkGreen,
                        uncheckedTrackColor = LightGray,
                    )
                )
            }

            Row(
                modifier = modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.age),
                    modifier = modifier.weight(1f),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                )

                OutlinedTextField(
                    value = state.year,
                    onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogYearChanged(it), context) },
                    modifier = modifier.weight(2f),
                    suffix = {
                        Text(
                            text = "year",
                            fontSize = 11.sp,
                            color = Black,
                            fontFamily = Poppins
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle.Default.copy(
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        color = (Black),
                        fontFamily = Poppins
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DisabledGreen,
                        unfocusedContainerColor = DisabledGreen,
                        disabledContainerColor = DisabledGreen,
                        errorContainerColor = DisabledGreen,
                        cursorColor = Black,
                        focusedBorderColor = (DarkGreen),
                        unfocusedBorderColor = (DarkGreen),
                    ),
                    supportingText = {
                        if (state.yearError != null) {
                            Text(
                                text = state.yearError,
                                color = Red
                            )
                        }
                    },
                    isError = state.yearError != null
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = modifier
                    .weight(2f)
            ) {
                Text(
                    text = stringResource(R.string.height),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = state.height,
                    onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogHeightChanged(it), context) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    suffix = {
                        Text(
                            text = "cm",
                            fontSize = 11.sp,
                            color = Black,
                            fontFamily = Poppins
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle.Default.copy(
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        color = (Black),
                        fontFamily = Poppins
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DisabledGreen,
                        unfocusedContainerColor = DisabledGreen,
                        disabledContainerColor = DisabledGreen,
                        errorContainerColor = DisabledGreen,
                        cursorColor = Black,
                        focusedBorderColor = (DarkGreen),
                        unfocusedBorderColor = (DarkGreen),
                    ),
                    supportingText = {
                        if (state.heightError != null) {
                            Text(
                                text = state.heightError,
                                color = Red
                            )
                        }
                    },
                    isError = state.heightError != null
                )
            }

            Column(
                modifier = modifier
                    .weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.gender),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )


                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }
                ) {
                    OutlinedTextField(
                        value = state.sex,
                        onValueChange = {},
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DisabledGreen,
                            unfocusedContainerColor = DisabledGreen,
                            disabledContainerColor = DisabledGreen,
                            errorContainerColor = DisabledGreen,
                            unfocusedBorderColor = DarkGreen,
                        ),
                        textStyle = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGreen
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {Text(
                                text="Male",
                                fontSize = 11.sp,
                            )},
                            onClick = {
                                 petViewModel.onEventAddDog(DogAddFormEvent.DogSexChanged("Male"), context)
                                    .toString()
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {Text(
                                text="Female",
                                fontSize = 11.sp,
                            )},
                            onClick = {
                                petViewModel.onEventAddDog(DogAddFormEvent.DogSexChanged("Female"), context)
                                    .toString()
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Column(
                modifier = modifier.weight(2f)
            ) {
                Text(
                    text = stringResource(R.string.weight),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = state.weight,
                    onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogWeightChanged(it), context) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    suffix = {
                        Text(
                            text = "kg",
                            fontSize = 11.sp,
                            color = Black,
                            fontFamily = Poppins
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle.Default.copy(
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        color = (Black),
                        fontFamily = Poppins
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DisabledGreen,
                        unfocusedContainerColor = DisabledGreen,
                        disabledContainerColor = DisabledGreen,
                        errorContainerColor = DisabledGreen,
                        cursorColor = Black,
                        focusedBorderColor = (DarkGreen),
                        unfocusedBorderColor = (DarkGreen),
                    ),
                    supportingText = {
                        if (state.weightError != null) {
                            Text(
                                text = state.weightError,
                                color = Red
                            )
                        }
                    },
                    isError = state.weightError != null
                )
            }
        }

        Text(
            text = stringResource(R.string.color),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )


        OutlinedTextField(
            value = state.color,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_dog_color),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogColorChanged(it), context) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                disabledContainerColor = LightGray,
                errorContainerColor = LightGray,
                cursorColor = Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.colorError != null) {
                    Text(
                        text = state.colorError,
                        color = Red
                    )
                }
            },
            isError = state.colorError != null
        )

        Text(
            text = stringResource(R.string.microchipid),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = state.microchipId,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_dog_microchipid),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogMicrochipIdChanged(it), context) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.fillMaxWidth(),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                disabledContainerColor = LightGray,
                errorContainerColor = LightGray,
                cursorColor = Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.microchipIdError != null) {
                    Text(
                        text = state.microchipIdError,
                        color = Red
                    )
                }
            },
            isError = state.microchipIdError != null
        )

        Text(
            text = stringResource(R.string.summary),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )


        OutlinedTextField(
            value = state.summary,
            placeholder = {
                Text(
                    text = stringResource(R.string.input_dog_summary),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { petViewModel.onEventAddDog(DogAddFormEvent.DogSummaryChanged(it), context) },
            singleLine = false,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 130.dp, max = 150.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                disabledContainerColor = LightGray,
                errorContainerColor = LightGray,
                cursorColor = Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.summaryError != null) {
                    Text(
                        text = state.summaryError,
                        color = Red
                    )
                }
            },
            isError = state.summaryError != null
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                petViewModel.onEventAddDog(DogAddFormEvent.Submit, context)
                showDialog(petViewModel.showDialog)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.save),
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
fun PetAddPreview() {
    PawraTheme {
        PetAdd(
            navController = rememberNavController(),
            petViewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
            ),
            showDialog = {}
        )
    }
}