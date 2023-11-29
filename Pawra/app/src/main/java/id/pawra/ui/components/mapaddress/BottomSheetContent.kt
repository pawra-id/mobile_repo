package id.pawra.ui.components.mapaddress

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.pawra.R
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DarkGreen
import id.pawra.ui.theme.PawraTheme
import id.pawra.ui.theme.White

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp, bottom = 20.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            text = "Set your address",
            color = Black,
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = modifier.padding(bottom = 10.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "Location Icon",
                modifier = modifier.padding(end = 20.dp)
            )

            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    text = "Jl. Brigjen Katamso No.160",
                    color = Black,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Jl. Brigjen Katamso No.160, Keparakan, Kec. Mergangsan, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55131",
                    color = Black,
                    textAlign = TextAlign.Start,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

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
                text = "Save",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomSheetContentPreview() {
    PawraTheme {
        BottomSheetContent()
    }
}