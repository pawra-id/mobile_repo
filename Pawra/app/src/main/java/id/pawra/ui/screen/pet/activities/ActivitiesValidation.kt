package id.pawra.ui.screen.pet.activities

import id.pawra.ui.components.addactivities.ChipData

class ValidateDog {
    fun execute(dog: String): ValidationResult {
        if(dog.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select your dog"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateActivity {
    fun execute(activity: String): ValidationResult {
        if(activity.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The activity can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateTags {
    fun execute(tags: MutableList<ChipData>): ValidationResult {
        if(tags.isNotEmpty()) if (tags[0].text.isEmpty()) tags.removeAt(0)
        if(tags.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please provide at least 1 tags"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)