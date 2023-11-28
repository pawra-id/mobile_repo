package id.pawra.data.local.preference

data class PetData(
    val image: String,
    val name: String,
    val breed: String,
    val neutred: Boolean,
    val age: Int,
    val height: Int,
    val gender: String,
    val weight: Int,
    val primaryColor: String,
    val microchipId: String?,
    val summary: String?,
)