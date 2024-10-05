package ramble.sokol.tgm_inverse

import dev.icerock.moko.mvvm.viewmodel.ViewModel

class MusicViewModel : ViewModel() {
    private var isPlaying: Boolean = false

    fun playMusic() {
        // Логика для запуска музыки
        isPlaying = true
    }

    fun stopMusic() {
        // Логика для остановки музыки
        isPlaying = false
    }

    fun isMusicPlaying(): Boolean = isPlaying
}
