package ramble.sokol.tgm_inverse

sealed class AppState {
    object Initial : AppState()
    data class Updated(val someValue: Long) : AppState()
}
