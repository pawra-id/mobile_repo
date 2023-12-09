package id.pawra.ui.components.addactivities

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import id.pawra.R
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.ChipsModel
import id.pawra.data.local.preference.PetData
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun ActivitiesPrevInfo (
    modifier: Modifier = Modifier,
    pet: PetResponseItem,
    viewModel: AuthViewModel
) {

    viewModel.getSession()
    val userInfo by viewModel.sessionState.collectAsState()

    val petData = PetData(
        pet.image ?: "",
        pet.name ?: "",
        pet.breed ?: "",
        pet.neutered ?: false,
        pet.age ?: 0,
        pet.height ?: 0,
        pet.gender ?: "",
        pet.weight ?: 0,
        pet.color ?: "",
        "",
        pet.description ?: ""
    )

    val selectedItems = mutableStateListOf<String>()
    var tagsInput by remember { mutableStateOf("") }
    var selectedTags by remember { mutableStateOf(listOf<String>()) }

    val inputList = listOf(
        ChipsModel(
            name = "Poop",
            textExpanded = "poop",
        ),
        ChipsModel(
            name = "Sad",
            textExpanded = "sad",
        ),
        ChipsModel(
            name = "Poop",
            textExpanded = "poop",
        )
    )

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
        ){
            Image(
                painterResource(id = R.drawable.background_profileimage),
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp),
                contentDescription = "Background Pet Profile Image",
            )
            Card(
                shape = CircleShape,
                modifier = Modifier
                    .padding(18.dp)
                    .size(130.dp)
            ) {
                AsyncImage(
                    model = userInfo.image,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .size(130.dp)
                )
            }
        }
        Text(
            text = petData.name,
            modifier = modifier
                .fillMaxWidth(),
            fontFamily = Poppins,
            color = Gray,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "17 Mei 2023",
            modifier = modifier
                .fillMaxWidth(),
            fontFamily = Poppins,
            color = Gray,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Lorem ipsum doloer sit amet",
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            fontFamily = Poppins,
            color = Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
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
    }
}

@Composable
@Preview(showBackground = true)
fun ActivitiesPrevInfoPreview() {
    PawraTheme {
        ActivitiesPrevInfo(
            pet = PetResponseItem(id=1),
            viewModel = viewModel(
                factory = ViewModelFactory(LocalContext.current)
        ))
    }
}