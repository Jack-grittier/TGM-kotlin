package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class SplashIconEntity(
    val id: Long,
    val enabled: Boolean,
    val fileURL: String,
)
