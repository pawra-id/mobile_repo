package id.pawra.data.local.preference

data class ActivityData(
    val description: String,
    val dogId: Int,
    val tags: List<Tags>?
)

data class Tags(
    val name: String
)