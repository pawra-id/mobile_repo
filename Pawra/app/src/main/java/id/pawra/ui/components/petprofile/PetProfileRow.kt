package id.pawra.ui.components.petprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.pawra.data.local.preference.PetData
import id.pawra.ui.theme.Black
import id.pawra.ui.theme.DisabledOrange
import id.pawra.ui.theme.Orange
import id.pawra.ui.theme.PawraTheme

@Composable
fun PetProfileRow(
    pet: PetData
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            InfoBox("Color", pet.primaryColor)
        }
        item {
            InfoBox("Weight", "${pet.weight} kg")
        }
        item {
            InfoBox("Height", "${pet.height} cm")
        }
        item {
            InfoBox("Microchip ID", pet.microchipId ?: "")
        }
    }
}

@Composable
fun InfoBox(title: String, value: String) {
    Box(
        modifier = Modifier
            .height(110.dp)
            .width(130.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(DisabledOrange),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(bottom = 4.dp),
                color = Orange
            )
            Text(
                text = value,
                fontWeight = FontWeight.Normal,
                color = Black
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PetProfilePreview() {
    PawraTheme {
        val petData = PetData(
            "https://static.vecteezy.com/system/resources/previews/005/857/332/non_2x/funny-portrait-of-cute-corgi-dog-outdoors-free-photo.jpg",
            "Max",
            "Corgi",
            true,
            1,
            24,
            "Male",
            75,
            "White, Gold",
            "123456",
            "A playful and friendly Corgi."
        )

        PetProfileRow(
            pet = petData
        )
    }
}