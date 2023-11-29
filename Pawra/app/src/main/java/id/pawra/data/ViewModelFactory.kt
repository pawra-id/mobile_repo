package id.pawra.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.pawra.data.auth.AuthRepository
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.vet.MapViewModel

class ViewModelFactory (
    private val authRepository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository) as T
        }
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}