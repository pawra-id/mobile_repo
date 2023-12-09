package id.pawra.ui.components.explore

sealed class Menu(val name: String) {
    data object MentalHealth : Menu("mental_health")
    data object Blogs : Menu("blogs")
}