package id.pawra.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkYellow
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledYellow
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Poppins
import id.pawra.ui.theme.White

@Composable
fun ConfirmationDialog(
    headText: String,
    warn: Boolean,
    message: String,
    yesText: String,
    cancelText: String,
    setShowDialog: (Boolean) -> Unit,
    action: () -> Unit
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
                        color = if (warn) DisabledYellow else DisabledBlue
                    ).padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    painter = if (warn) painterResource(id = R.drawable.warning_icon) else painterResource(id = R.drawable.question_icon),
                    contentDescription = if (warn) "Warning Icon" else "Question Icon",
                    tint = Color.Unspecified
                )

                Text(
                    text = headText,
                    fontSize = 18.sp,
                    color = if (warn) DarkYellow else DarkBlue,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 26.dp, end = 26.dp)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 26.dp, end = 26.dp, bottom = 36.dp),
                    text = message,
                    color = if (warn) DarkYellow else DarkBlue,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)

                ) {
                    Button(
                        modifier = Modifier.width(130.dp).height(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { setShowDialog(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White
                        )
                    ) {
                        Text(
                            text = cancelText,
                            fontSize = 12.sp,
                            color = if (warn) DarkYellow else DarkBlue,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        modifier = Modifier.width(130.dp).height(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            action()
                            setShowDialog(false)
                                  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (warn) DarkYellow else DarkBlue
                        )
                    ) {
                        Text(
                            text = yesText,
                            fontSize = 12.sp,
                            fontFamily = Poppins,
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
fun ConfirmationDialogPreview() {
    PawraTheme {
        ConfirmationDialog(
            "Confirm Delete",
            true,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec porttitor quis nisi a fringilla. Etiam tempor orci in nisl consectetur, ac venenatis eros eleifend. Morbi massa odio, rhoncus quis iaculis ullamcorper, vestibulum in enim.",
            "Yes, delete",
            "cancel",
            {},
            {})
    }
}