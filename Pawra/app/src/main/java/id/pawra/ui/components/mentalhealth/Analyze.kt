package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun Analyze(
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog) {
        AnalyzePopUpForm(
            setShowDialog = {
                showDialog = it
            }
        )
    }

    Column(
        modifier = modifier
            .padding(horizontal = 22.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {

        Button(
            modifier = modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .height(56.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                showDialog = true
            }
        ) {
            Text(
                text = "Analyze",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        var isFinished by remember { mutableStateOf(true) }

        Box(
            modifier = modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = colorResource(id = R.color.disabled_green))
                .clickable {
                    if (isFinished) {

                    }
                },
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isFinished) "Analyzing mental health finished" else "Analyzing mental health",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGreen,
                    textAlign = TextAlign.Center
                )

                if (isFinished) {
                    Text(
                        text = "Click this card to see the result",
                        fontSize = 13.sp,
                        color = DarkGreen,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.success_icon),
                        contentDescription = "Success Icon",
                        tint = Color.Unspecified,
                        modifier = modifier
                            .size(80.dp)
                            .padding(top = 15.dp)
                    )
                } else {
                    LinearProgressIndicator(
                        progress = 0.45f,
                        color = DarkGreen,
                        trackColor = White,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(width = 280.dp, height = 13.dp)
                    )

                    Text(
                        text = "45 % ...",
                        fontSize = 13.sp,
                        color = DarkGreen,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AnalyzePreview() {
    PawraTheme {
        Analyze()
    }
}