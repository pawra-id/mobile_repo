package id.pawra.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.pawra.data.repository.ActivitiesRepository
import id.pawra.data.repository.AuthRepository
import id.pawra.data.repository.PetRepository
import id.pawra.data.repository.VetRepository
import id.pawra.di.Injection
import id.pawra.ui.screen.auth.AuthViewModel
import id.pawra.ui.screen.pet.activities.ActivitiesViewModel
import id.pawra.ui.screen.pet.profile.PetViewModel
import id.pawra.ui.screen.vet.MapViewModel
import id.pawra.ui.screen.vet.VetViewModel

class ViewModelFactory (
    private val context: Context,
    private val authRepository: AuthRepository = Injection.provideAuthRepository(context),
    private val petRepository: PetRepository = Injection.providePetRepository(context),
    private val activitiesRepository: ActivitiesRepository = Injection.provideActivitiesRepository(context),
    private val vetRepository: VetRepository = Injection.provideVetRepository(context),
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository) as T
        }
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel() as T
        }
        if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            return PetViewModel(petRepository, authRepository) as T
        }
        if (modelClass.isAssignableFrom(ActivitiesViewModel::class.java)) {
            return ActivitiesViewModel(activitiesRepository, authRepository) as T
        }
        if (modelClass.isAssignableFrom(VetViewModel::class.java)) {
            return VetViewModel(vetRepository, authRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}