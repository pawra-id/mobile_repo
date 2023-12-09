package id.pawra.data.local.preference

data class MentalHealthData(
    val petName: String,
    val petPercentage: Float,
    val titleResult: String,
    val descResult: String,
    val recommendedActions: List<String>? = null
)