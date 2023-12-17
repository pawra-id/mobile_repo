package id.pawra.ui.components.petprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import id.pawra.data.ViewModelFactory
import id.pawra.ui.components.dialog.ConfirmationDialog
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.navigation.Screen
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.LightGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red

@Composable
fun PetProfileTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    petId: Int,
    onDeleteClick: () -> Unit
) {
    var displayMenu by remember { mutableStateOf(false)}
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .background(LightGreen)
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 15.dp),
    ) {
        IconButton(
            onClick = {  },
            modifier = modifier
                .background(DisabledGreen, CircleShape)
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back",
                tint = DarkGreen,
                modifier = modifier.size(18.dp)
            )
        }

        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Dog Profile",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = DarkGreen,
                modifier = modifier
            )
        }

        Box(
            modifier = modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            IconButton(
                modifier = modifier
                    .size(32.dp),
                onClick = { displayMenu = !displayMenu }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More",
                    tint = DarkGreen,
                    modifier = modifier.size(25.dp),
                )
            }

            Box(
                contentAlignment = Alignment.CenterEnd
            ){
                DropdownMenu(
                    expanded = displayMenu,
                    onDismissRequest = { displayMenu = false},
                    modifier = Modifier
                        .padding(end = 16.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text(
                            text = "Edit",
                            color = DarkGreen
                        ) },
                        onClick = {
                            navController.navigate(Screen.PetUpdate.createRoute(petId))
                            displayMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit",
                                tint = DarkGreen
                            )
                        })
                    DropdownMenuItem(
                        text = { Text(
                            text = "Delete",
                            color = Red
                        ) },
                        onClick = {
                            showDeleteConfirmation = true
                            displayMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = Red
                            )
                        })
                }
            }

        }
    }
    if (showDeleteConfirmation) {
        ConfirmationDialog(
            headText = "Delete Confirmation",
            warn = false,
            message = "Are you sure you want to delete this?",
            setShowDialog = { showDeleteConfirmation = it },
            action = {
                onDeleteClick()
                showResultDialog = true
            }
        )
    }

    if (showResultDialog) {
        ResultDialog(
            success = true,
            message = "Successful deletion",
            setShowDialog = {
                showResultDialog = it
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.PetProfile.route) {
                        inclusive = true
                    }
                }
                            },
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PetProfileTopBarPreview(
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
    PawraTheme {
        PetProfileTopBar(
            navController = rememberNavController(),
            petId = 0,
            onDeleteClick = ({
                petViewModel.deleteDogId(0)
            })
        )
    }
}