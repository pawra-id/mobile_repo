package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import id.pawra.R
import id.pawra.ui.screen.auth.SignUpFormEvent
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red
import id.pawra.ui.theme.White

@Composable
fun AnalyzePopUpForm(
    modifier: Modifier = Modifier,
    setShowDialog: (Boolean) -> Unit,
) {
    var lastXDays by rememberSaveable { mutableStateOf("") }
    var numberOfActivities by rememberSaveable { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 15.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White)
                    .padding(vertical = 16.dp, horizontal = 22.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    modifier = modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            setShowDialog(false)
                        },
                    tint = Black
                )

                Column {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp, top = 25.dp),
                        text = "Amount of data you want to analyze",
                        color = Black,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp,
                    )

                    Text(
                        text = "Last x days",
                        color = colorResource(id = R.color.gray),
                        fontSize = 14.sp,
                        modifier = modifier
                            .fillMaxWidth())

                    OutlinedTextField(
                        value = lastXDays,
                        onValueChange = { lastXDays = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray),
                            fontFamily = Poppins
                        ),
                        isError = false,
                    )

                    Text(
                        text = "Number of activities",
                        color = colorResource(id = R.color.gray),
                        fontSize = 14.sp,
                        modifier = modifier
                            .fillMaxWidth())

                    OutlinedTextField(
                        value = numberOfActivities,
                        onValueChange = { numberOfActivities = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        textStyle = TextStyle.Default.copy(
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray),
                            fontFamily = Poppins
                        ),
                        isError = false,
                    )

                    Button(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .height(56.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreen
                        )
                    ) {
                        Text(
                            text = "Enter",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun AnalyzePopUpFormPreview() {
    PawraTheme {
        AnalyzePopUpForm(setShowDialog = {})
    }
}