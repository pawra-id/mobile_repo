package id.pawra.ui.components.mentalhealth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.ui.theme.DarkBlue
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.DarkYellow
import id.pawra.ui.theme.DisabledBlue
import id.pawra.ui.theme.DisabledGreen
import id.pawra.ui.theme.DisabledRed
import id.pawra.ui.theme.DisabledYellow
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.Red
import kotlin.math.roundToInt

@Composable
fun MentalHealthResultGraph (
    percentage: Float
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PercentageCircleProgressBar(percentage = percentage)
        }
    }
}



@Composable
fun PercentageCircleProgressBar(percentage: Float, modifier: Modifier = Modifier) {

    val color: Color
    val disableColor: Color

    if (percentage < 25) {
        color = DarkGreen
        disableColor = DisabledGreen
    } else if(percentage < 50) {
        color = DarkBlue
        disableColor = DisabledBlue
    } else if(percentage < 75) {
        color = DarkYellow
        disableColor = DisabledYellow
    } else {
        color = Red
        disableColor = DisabledRed
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
        ) {
            val canvasWidth = size.width - 32.dp.toPx()
            val canvasHeight = size.height - 32.dp.toPx()

            val strokeWidth = 60.dp.toPx()
            val startAngle = -90f
            val sweepAngle = 360f * (percentage / 100)

            val offsetX = (size.width - canvasWidth) / 2
            val offsetY = (size.height - canvasHeight) / 2

            drawArc(
                color = disableColor,
                startAngle = startAngle,
                sweepAngle = 360f,
                useCenter = false,
                size = size.copy(width = canvasWidth, height = canvasHeight),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(offsetX, offsetY)
            )

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                size = size.copy(width = canvasWidth, height = canvasHeight),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(offsetX, offsetY)
            )
        }

        Text(
            text = "${percentage.roundToInt()}%",
            modifier = Modifier
                .align(Alignment.Center),
            fontFamily = FontFamily.Default,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MentalHealthResultGraphPreview() {
    PawraTheme {
        MentalHealthResultGraph(
            percentage = 11f
        )
    }
}