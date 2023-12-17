package id.pawra.ui.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash_screen")
    data object OnBoarding : Screen("onboarding")
    data object SignUp : Screen("signup")
    data object SignIn : Screen("signin")

    // Main Navigation
    data object Home : Screen("home")
    data object Pet : Screen("pet")
    data object Explore : Screen("explore")
    data object Profile : Screen("profile")

    // Explore Navigation
    data object BlogDetail : Screen("blogs/{blogsId}") {
        fun createRoute(blogsId: Int) = "blogs/$blogsId"
    }

    // Profile Navigation
    data object EditProfile : Screen("edit_profile")

    // Pet Navigation
    data object PetProfile : Screen("pet_profile/{petId}"){
        fun createRoute(petId: Int) = "pet_profile/$petId"
    }
    data object PetUpdate : Screen("pet_update/{petId}"){
        fun createRoute(petId: Int) = "pet_update/$petId"
    }
    data object PetAdd : Screen("pet_add")
    data object PetActivities : Screen("pet_activities")
    data object PetActivitiesAdd : Screen("pet_activities_add?petId={petId}"){
        fun createRoute(petId: Int) = "pet_activities_add?petId=$petId"
    }
    data object PetActivitiesPrev : Screen("pet_activities_prev/{activityId}") {
        fun createRoute(activityId: Int) = "pet_activities_prev/$activityId"
    }
    data object PetActivitiesUpdate : Screen("pet_activities_update/{activityId}"){
        fun createRoute(activityId: Int) = "pet_activities_update/$activityId"
    }
    data object PetMentalHealth : Screen("pet_mental_health")
    data object PetMentalHealthResult : Screen("pet_mental_health_result/{analysisId}") {
        fun createRoute(analysisId: Int) = "pet_mental_health_result/$analysisId"
    }


    // Vet Navigation
    data object Vets : Screen("vets")
    data object MapAddress : Screen("map_address")
    data object VetProfile : Screen("vet_profile/{vetId}") {
        fun createRoute(vetId: Int) = "vet_profile/$vetId"
    }
}