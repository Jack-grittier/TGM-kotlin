package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class SplashScreenIconResponse(
    val id: String,
    val url: String,
    val enabled: Boolean,
)
