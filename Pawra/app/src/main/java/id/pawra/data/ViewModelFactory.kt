package id.pawra.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.pawra.data.auth.AuthRepository
import id.pawra.ui.screen.auth.AuthViewModel

class ViewModelFactory (
    private val authRepository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}