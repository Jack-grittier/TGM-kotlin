package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class ListensMusicEntityEntity(
    val musicId: Long,
    val listeningTime: Long,
)
