package id.pawra.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.pawra.R
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledRed
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.Red

@Composable
fun ResultDialog(
    success: Boolean,
    message: String,
    setShowDialog: (Boolean) -> Unit,
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
            shape = RoundedCornerShape(size = 15.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (success) DisabledGreen else DisabledRed
                    ).padding(vertical = 16.dp),
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
                        .padding(top = 16.dp, start = 26.dp, end = 26.dp, bottom = 36.dp),
                    text = message,
                    color = if (success) DarkGreen else Red,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium
                )

                Button(
                    modifier = Modifier.width(100.dp).height(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        setShowDialog(false)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (success) DarkGreen else Red
                    )
                ) {
                    Text(
                        text = "OK",
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ResultDialogPreview() {
    PawraTheme {
        ResultDialog(true,
            ""
        ) {}
    }
}