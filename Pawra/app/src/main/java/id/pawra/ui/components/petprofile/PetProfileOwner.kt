package id.pawra.ui.components.petprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.pawra.data.ViewModelFactory
import id.pawra.data.local.preference.SessionModel
import id.pawra.di.Injection
import id.pawra.ui.common.UiState
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.Gray
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins

@Composable
fun PetProfileOwner(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
) {
    viewModel.getSession()
    val sessionState by viewModel.sessionState.collectAsState()

    Box(
        modifier = modifier
            .width(306.dp)
            .heightIn(min = 81.dp, max = 135.dp)
            .background(DisabledBlue)
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.CenterStart
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            when (sessionState) {
                is UiState.Success -> {
                    val userInfo = (sessionState as UiState.Success<SessionModel>).data

                    Text(
                        text = userInfo.name,
                        color = Black,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                    )

                    Text(
                        text = "Owner",
                        color = DarkBlue,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                    )

                    Text(
                        text = userInfo.email,
                        color = Gray,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                    )
                }
                is UiState.Error -> {

                    Text(text = "Error: ${(sessionState as UiState.Error).errorMessage}")
                }

                else -> {
                    Text(text = "Loading...")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PetProfileOwnerPreview() {
    PawraTheme {
        PetProfileOwner(viewModel = viewModel(
            factory = ViewModelFactory(LocalContext.current)
        ))
    }
}