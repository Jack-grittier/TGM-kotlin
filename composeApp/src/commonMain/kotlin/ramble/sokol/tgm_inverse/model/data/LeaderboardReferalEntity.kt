package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardReferalEntity(
    val position: Long,
    val username: String,
    val amount: Long,
)
