package id.pawra.ui.screen.pet.activities

sealed class AddActivityFormEvent {
    data class DogChanged(val dog: String) : AddActivityFormEvent()
    data class ActivityChanged(val activity: String) : AddActivityFormEvent()
    data class TagsChanged(val tags: String) : AddActivityFormEvent()

    data object Submit: AddActivityFormEvent()
}

sealed class UpdateActivityFormEvent {
    data class DogChanged(val dog: String) : UpdateActivityFormEvent()
    data class ActivityChanged(val activity: String) : UpdateActivityFormEvent()
    data class TagsChanged(val tags: String) : UpdateActivityFormEvent()

    data object Update: UpdateActivityFormEvent()
}