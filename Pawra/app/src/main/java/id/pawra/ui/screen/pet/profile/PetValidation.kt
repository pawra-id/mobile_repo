package id.pawra.ui.screen.pet.profile

class ValidateDogName {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogBreed {
    fun execute(breed: String): ValidationResult {
        if(breed.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The breed can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

fun isNumeric(str: String): Boolean {
    return str.all { it.isDigit() }
}

val noSpaceRegex = Regex("^[^ ]*$")

class ValidateDogYear {
    fun execute(year: String): ValidationResult {
        if(year.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The year can't be blank"
            )
        }

        if(!isNumeric(year)){
            return ValidationResult(
                successful = false,
                errorMessage = "The year only contains number"
            )
        }

        if(!noSpaceRegex.matches(year)){
            return ValidationResult(
                successful = false,
                errorMessage = "The year doesn't contains space"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogHeight {
    fun execute(height: String): ValidationResult {
        if(height.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The height can't be blank"
            )
        }

        if(!isNumeric(height)){
            return ValidationResult(
                successful = false,
                errorMessage = "The height only contains number"
            )
        }

        if(!noSpaceRegex.matches(height)){
            return ValidationResult(
                successful = false,
                errorMessage = "The height doesn't contains space"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogWeight {
    fun execute(weight: String): ValidationResult {
        if(weight.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The weight can't be blank"
            )
        }

        if(!isNumeric(weight)){
            return ValidationResult(
                successful = false,
                errorMessage = "The weight only contains number"
            )
        }

        if(!noSpaceRegex.matches(weight)){
            return ValidationResult(
                successful = false,
                errorMessage = "The weight doesn't contains space"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogColor {
    fun execute(color: String): ValidationResult {
        if(color.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The color can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogMicrochipId {
    fun execute(microchipId: String): ValidationResult {
        if(microchipId.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The microchipId can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogSummary {
    fun execute(summary: String): ValidationResult {
        if(summary.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The summary can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}

class ValidateDogImage {
    fun execute(file: String): ValidationResult {
        if(file.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "please provide image"
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