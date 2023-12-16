package id.pawra.ui.screen.pet.activities

data class AddActivityFormState(
    var dog: String = "",
    var dogId: Int = 0,
    val dogError: String? = null,
    val activity: String = "",
    val activityError: String? = null,
    var tags: String = "",
    val tagsError: String? = null,
)

data class UpdateActivityFormState(
    var id: Int = 0,
    var dog: String = "",
    var dogId: Int = 0,
    val dogError: String? = null,
    var activity: String = "",
    val activityError: String? = null,
    var tags: String = "",
    val tagsError: String? = null,
)