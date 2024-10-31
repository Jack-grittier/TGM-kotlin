package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class RewardGameEntity(
    val musicId: Long? = null,
    val seconds: Long? = null
)
