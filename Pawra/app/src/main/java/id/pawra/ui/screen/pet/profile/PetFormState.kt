package id.pawra.ui.screen.pet.profile

import okhttp3.MultipartBody

data class DogAddFormState(
    val name: String = "",
    val nameError: String? = null,
    val breed: String = "",
    val breedError: String? = null,
    val neutered: Boolean = false,
    val year: String = "",
    val yearError: String? = null,
    val height: String = "",
    val heightError: String? = null,
    val sex: String = "Male",
    val weight: String = "",
    val weightError: String? = null,
    val color: String = "",
    val colorError: String? = null,
    val microchipId: String = "",
    val microchipIdError: String? = null,
    val summary: String = "",
    val summaryError: String? = null,
    var file: String = "",
    val fileError: String? = null,
)

data class DogUpdateFormState(
    val name: String = "",
    val nameError: String? = null,
    val breed: String = "",
    val breedError: String? = null,
    val neutered: Boolean = false,
    val year: String = "",
    val yearError: String? = null,
    val height: String = "",
    val heightError: String? = null,
    val sex: Boolean = false,
    val weight: String = "",
    val weightError: String? = null,
    val color: String = "",
    val colorError: String? = null,
    val microchipId: String = "",
    val microchipIdError: String? = null,
    val summary: String = "",
    val summaryError: String? = null,
)