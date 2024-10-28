package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class ListensMusicEntityResponse(
    val musicId: Long? = null,
    val listeningTime: Long? = null,
    val listened: Boolean? = null,
    val statusCode: Int? = null,
)
