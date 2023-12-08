package id.pawra.ui.screen.pet.activities

import id.pawra.data.local.preference.Tags

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
    var dog: String = "",
    var dogId: Int = 0,
    val dogError: String? = null,
    val activity: String = "",
    val activityError: String? = null,
    var tags: List<Tags> = listOf(Tags("")) ,
    val tagsError: String? = null,
)