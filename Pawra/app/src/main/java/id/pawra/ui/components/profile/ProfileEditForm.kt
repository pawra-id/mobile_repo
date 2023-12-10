package id.pawra.ui.components.profile

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.auth.SignInFormEvent
import id.pawra.ui.screen.auth.UpdateProfileFormEvent
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.LightGray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red
import id.pawra.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@Composable
fun ProfileEditForm(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    showDialog: (Boolean) -> Unit,
    navController: NavController
) {
    val state = viewModel.stateUpdateProfile
    val context = LocalContext.current

    var imageUri by rememberSaveable { mutableStateOf("") }
    var isImageUpdate by rememberSaveable { mutableStateOf(false) }

    viewModel.getSession()
    val userInfo = viewModel.sessionState.collectAsState().value

    LaunchedEffect(Unit){
        state.name =  userInfo.name
        state.email = userInfo.email
        state.summary = userInfo.summary
    }

    Column(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileEditImage(
            userInfo.image,
            updateImage = { imageUri = it },
            isImageUpdate = { isImageUpdate = it }
        )

        Text(
            text = stringResource(R.string.name),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        OutlinedTextField(
            value = state.name,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { viewModel.onEventUpdateProfile(UpdateProfileFormEvent.NameChanged(it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.nameError != null) {
                    Text(
                        text = state.nameError ?: "",
                        color = Red,
                    )
                }
            },
            isError = state.nameError != null
        )

        Text(
            text = stringResource(R.string.email),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        OutlinedTextField(
            value = state.email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.onEventUpdateProfile(UpdateProfileFormEvent.EmailChanged(it)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.emailError != null) {
                    Text(
                        text = state.emailError ?: "",
                        color = Red,
                    )
                }
            },
            isError = state.emailError != null
        )

        Text(
            text = stringResource(R.string.summary),
            color = Gray,
            fontSize = 14.sp,
            modifier = modifier
                .fillMaxWidth())

        OutlinedTextField(
            value = state.summary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { viewModel.onEventUpdateProfile(UpdateProfileFormEvent.SummaryChanged(it)) },
            singleLine = false,
            maxLines = 5,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .size(120.dp),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                color = (Black),
                fontFamily = Poppins
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedBorderColor = (DarkGreen),
                unfocusedBorderColor = (DarkGreen),
            ),
            supportingText = {
                if (state.summaryError != null) {
                    Text(
                        text = state.summaryError ?: "",
                        color = Red,
                    )
                }
            },
            isError = state.summaryError != null
        )

        Button(
            onClick = {
                var multipartBody: MultipartBody.Part? = null
                if (isImageUpdate) {
                    val imageFile = uriToFile(Uri.parse(imageUri), context)
                    val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                    multipartBody = MultipartBody.Part.createFormData(
                        "file",
                        imageFile.name,
                        requestImageFile
                    )
                }
                viewModel.updateDataProfile(
                    userInfo.id,
                    userInfo.token,
                    state.name,
                    state.email,
                    state.summary,
                    userInfo.password,
                    imageUri,
                    multipartBody
                )
                showDialog(viewModel.showDialog)
                viewModel.getSession()
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
    }

}

@Composable
@Preview(showBackground = true)
fun ProfileEditFormPreview() {
    PawraTheme {
        ProfileEditForm(viewModel = viewModel(
            factory = ViewModelFactory(LocalContext.current)
        ),
            showDialog = {  }
            ,navController = rememberNavController())
    }
}