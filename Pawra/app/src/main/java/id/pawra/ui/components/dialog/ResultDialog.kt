package id.pawra.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import id.pawra.R
import id.pawra.ui.theme.PawraTheme

@Composable
fun ResultDialog(
    success: Boolean,
    message: String,
    setShowDialog: (Boolean) -> Unit,
    setLoading: (Boolean) -> Unit,
) {
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 12.dp)
        ) {

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (success) colorResource(id = R.color.disabled_green) else colorResource(
                                id = R.color.disabled_red
                            )
                        ),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 26.dp)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(60.dp),
                            painter = if (success) painterResource(id = R.drawable.success_icon) else painterResource(id = R.drawable.fail_icon),
                            contentDescription = if (success) "Success Icon" else "Fail Icon",
                            tint = Color.Unspecified
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 26.dp, end = 26.dp),
                            text = message,
                            color = colorResource(id = R.color.white),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    Button(
                        modifier = Modifier.width(100.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            setShowDialog(false)
                            setLoading(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (success) colorResource(id = R.color.light_green) else colorResource(id = R.color.light_red)
                        )
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ResultDialogPreview() {
    PawraTheme {
        ResultDialog(true, "Success", {}, {})
    }
}