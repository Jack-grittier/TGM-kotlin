package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val id: Long,
    val name: String,
    val group: String,
    val url: String,
    val coverURL: String,
    val reward: Long,
)
