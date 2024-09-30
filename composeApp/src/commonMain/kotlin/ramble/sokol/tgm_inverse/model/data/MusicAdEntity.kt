package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class MusicAdEntity(
    val musicId: Long? = null,
    val duration: DurationAdEntity? = null,
    val statusCode: Int? = null,
)
