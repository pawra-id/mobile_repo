package id.pawra.ui.components.addpet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import id.pawra.R
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAdd(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        imageUri.value.ifEmpty { R.drawable.ic_pet }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    var name by rememberSaveable { mutableStateOf("") }
    var breed by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("")}
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var color by rememberSaveable { mutableStateOf("") }
    var microchip_id by rememberSaveable { mutableStateOf("") }
    var summary by rememberSaveable { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf("Male") }

    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(12.dp)
                    .border(2.dp, DarkGreen, CircleShape)
                    .size(125.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Add Profile Image",
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

        Text(
            text = stringResource(R.string.name),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .drawBehind {
                    drawRoundRect(
                        color = (LightGray),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }
        ) {
            OutlinedTextField(
                value = name,
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_dog_name),
                        color = Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { name = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    color = (Black),
                    fontFamily = Poppins
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = (DarkGreen),
                    unfocusedBorderColor = (DarkGreen),
                    cursorColor = Black,
                )
            )
        }

        Text(
            text = stringResource(R.string.breed),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .drawBehind {
                    drawRoundRect(
                        color = (LightGray),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }
        ) {
            OutlinedTextField(
                value = breed,
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_dog_breed),
                        color = Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { breed = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    color = (Black),
                    fontFamily = Poppins
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = (DarkGreen),
                    unfocusedBorderColor = (DarkGreen),
                    cursorColor = Black,
                )
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {

            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp, bottom = 8.dp),
                enabled = true,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = DarkGreen,
                    checkedTrackColor = DisabledGreen,
                    uncheckedThumbColor = DarkGreen,
                    uncheckedTrackColor = LightGray,
                )
            )

            Text(
                text = stringResource(R.string.neutred),
                modifier = Modifier
                    .padding(end = 65.dp, top = 20.dp),
                color = Gray,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
            )

            Box(
                modifier = modifier
                    .width(100.dp)
                    .height(60.dp)
                    .padding(top = 10.dp, end = 8.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = (LightGreen),
                            size = Size(size.width, size.height),
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                        )
                    }
            ) {
                OutlinedTextField(
                    value = age,
                    onValueChange = {age = it },
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp),
                    suffix = {
                        Text(
                            text = "year",
                            fontSize = 11.sp,
                            color = Black,
                            fontFamily = Poppins
                        )
                    },
                    supportingText = {
                        Text(
                            text = "*enter an integer",
                            fontSize = 10.sp,
                            color = Black,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    isError = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle.Default.copy(
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        color = (Black),
                        fontFamily = Poppins
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = (DarkGreen),
                        unfocusedBorderColor = (DarkGreen),
                        cursorColor = Black,
                    )
                )
            }
            Text(
                text = stringResource(R.string.age),
                modifier = Modifier
                    .padding(end = 8.dp, top = 25.dp),
                color = Gray,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.height),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = modifier
                        .width(100.dp)
                        .height(60.dp)
                        .padding(top = 8.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = (LightGreen),
                                size = Size(size.width, size.height),
                                cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                            )
                        }
                ) {
                    OutlinedTextField(
                        value = height,
                        onValueChange = {height = it },
                        modifier = Modifier
                            .width(100.dp)
                            .height(60.dp),
                        suffix = {
                            Text(
                                text = "inch",
                                fontSize = 11.sp,
                                color = Black,
                                fontFamily = Poppins
                            )
                        },
                        supportingText = {
                            Text(
                                text = "*enter an integer",
                                fontSize = 10.sp,
                                color = Black,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle.Default.copy(
                            textAlign = TextAlign.Right,
                            fontSize = 14.sp,
                            color = (Black),
                            fontFamily = Poppins
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = (DarkGreen),
                            unfocusedBorderColor = (DarkGreen),
                            cursorColor = Black,
                        )
                    )
                }
            }

            Column(
                modifier = modifier
                    .width(130.dp)
                    .height(100.dp)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.gender),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = modifier
                        .width(130.dp)
                        .height(60.dp)
                        .padding(top = 8.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = (LightGreen),
                                size = Size(size.width, size.height),
                                cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                            )
                        }
                ) {
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .width(130.dp)
                                .height(60.dp),
                            readOnly = true,
                            shape = RoundedCornerShape(10.dp),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
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
                                    gender = "Male"
                                    isExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = {Text(
                                    text="Female",
                                    fontSize = 11.sp,
                                )},
                                onClick = {
                                    gender = "Female"
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Column(
                modifier = modifier
                    .width(100.dp)
                    .height(100.dp)
            ) {
                Text(
                    text = stringResource(R.string.weight),
                    color = Gray,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = modifier
                        .width(100.dp)
                        .height(60.dp)
                        .padding(top = 8.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = (LightGreen),
                                size = Size(size.width, size.height),
                                cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                            )
                        }
                ) {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        modifier = Modifier
                            .width(100.dp)
                            .height(60.dp),
                        suffix = {
                            Text(
                                text = "pound",
                                fontSize = 11.sp,
                                color = Black,
                                fontFamily = Poppins
                            )
                        },
                        supportingText = {
                            Text(
                                text = "*enter an integer",
                                fontSize = 10.sp,
                                color = Black,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        isError = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle.Default.copy(
                            textAlign = TextAlign.Right,
                            fontSize = 14.sp,
                            color = (Black),
                            fontFamily = Poppins
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = (DarkGreen),
                            unfocusedBorderColor = (DarkGreen),
                            cursorColor = Black,
                        )
                    )
                }
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

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .drawBehind {
                    drawRoundRect(
                        color = (LightGray),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }
        ) {
            OutlinedTextField(
                value = color,
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_dog_color),
                        color = Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { color = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    color = (Black),
                    fontFamily = Poppins
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = (DarkGreen),
                    unfocusedBorderColor = (DarkGreen),
                    cursorColor = Black,
                )
            )
        }

        Text(
            text = stringResource(R.string.microchipid),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .drawBehind {
                    drawRoundRect(
                        color = (LightGray),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }
        ) {
            OutlinedTextField(
                value = microchip_id,
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_dog_microchipid),
                        color = Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { microchip_id = it },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                modifier = modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    color = (Black),
                    fontFamily = Poppins
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = (DarkGreen),
                    unfocusedBorderColor = (DarkGreen),
                    cursorColor = Black,
                )
            )
        }

        Text(
            text = stringResource(R.string.summary),
            color = Gray,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            modifier = modifier
                .fillMaxWidth()
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .drawBehind {
                    drawRoundRect(
                        color = (LightGray),
                        size = Size(size.width, size.height),
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }
        ) {
            OutlinedTextField(
                value = summary,
                placeholder = {
                    Text(
                        text = stringResource(R.string.input_dog_summary),
                        color = Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = {summary = it },
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = (DarkGreen),
                    unfocusedBorderColor = (DarkGreen),
                    cursorColor = Black,
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
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
            navController = rememberNavController()
        )
    }
}