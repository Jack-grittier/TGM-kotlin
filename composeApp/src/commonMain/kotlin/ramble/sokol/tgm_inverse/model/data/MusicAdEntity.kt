package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class MusicAdEntity(
    val musicId: Long?,
    val duration: DurationAdEntity?,
    val statusCode: Int?,
)
