package id.pawra.ui.screen.pet.profile

sealed class DogAddFormEvent {
    data class DogNameChanged(val name: String) : DogAddFormEvent()
    data class DogBreedChanged(val breed: String) : DogAddFormEvent()
    data class DogYearChanged(val year: String) : DogAddFormEvent()
    data class DogHeightChanged(val height: String) : DogAddFormEvent()
    data class DogWeightChanged(val weight: String) : DogAddFormEvent()
    data class DogColorChanged(val color: String) : DogAddFormEvent()
    data class DogMicrochipIdChanged(val microchipId: String) : DogAddFormEvent()
    data class DogSummaryChanged(val summary: String) : DogAddFormEvent()
    data class DogSexChanged(val sex: String) : DogAddFormEvent()
    data class DogNeuteredChanged(val neutered: Boolean) : DogAddFormEvent()
    data class DogImageChanged(val file: String) : DogAddFormEvent()

    data object Submit: DogAddFormEvent()
}

sealed class DogUpdateFormEvent {
    data class DogNameChanged(val name: String) : DogUpdateFormEvent()
    data class DogBreedChanged(val breed: String) : DogUpdateFormEvent()
    data class DogYearChanged(val year: String) : DogUpdateFormEvent()
    data class DogHeightChanged(val height: String) : DogUpdateFormEvent()
    data class DogSexChanged(val sex: String) : DogUpdateFormEvent()
    data class DogWeightChanged(val weight: String) : DogUpdateFormEvent()
    data class DogColorChanged(val color: String) : DogUpdateFormEvent()
    data class DogMicrochipIdChanged(val microchipId: String) : DogUpdateFormEvent()
    data class DogSummaryChanged(val summary: String) : DogUpdateFormEvent()
    data class DogNeuteredChanged(val neutered: Boolean) : DogUpdateFormEvent()
    data class DogImageChanged(val file: String) : DogUpdateFormEvent()
}