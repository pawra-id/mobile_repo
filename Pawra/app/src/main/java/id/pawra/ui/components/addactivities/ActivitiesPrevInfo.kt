package id.pawra.ui.components.addactivities

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.data.remote.response.PetResponseItem
import id.pawra.ui.common.UiState
import id.pawra.ui.components.dialog.ResultDialog
import id.pawra.ui.components.loading.LoadingBox
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.utils.DateConverter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun ActivitiesPrevInfo (
    modifier: Modifier = Modifier,
    activity: ActivitiesResponseItem,
    petViewModel: PetViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
) {
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
                    model = activity.dog?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .size(130.dp)
                )
            }
        }
        Text(
            text = activity.dog?.name ?: "",
            modifier = modifier
                .fillMaxWidth(),
            fontFamily = Poppins,
            color = Gray,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = DateConverter.convertStringToDate(activity.createdAt ?: ""),
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
            text = activity.description ?: "",
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
            items(activity.tags ?: listOf()) { item ->

                Spacer(modifier = Modifier.padding(5.dp))
                InputChip(
                    selected = true,
                    onClick = {

                    },
                    label = { Text(
                        text = item.name ?: "",
                        color = DarkGreen
                    )
                    }
                )
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun ActivitiesPrevInfoPreview() {
    PawraTheme {
        ActivitiesPrevInfo(
            activity = ActivitiesResponseItem(id = 0)
        )
    }
}