package id.pawra.data.local.preference

data class VetData(
    val image: String,
    val name: String,
    val rangeLocation: Double,
    val experience: Int,
    val startWorkingHours: Int,
    val endWorkingHours: Int,
    val workPlace: String,
    val gender: String,
    val whatsappNumber: Int,
    val email: String,
    val location: String,
    val lat: Double,
    val lng: Double,
    val studies: List<String>,
)