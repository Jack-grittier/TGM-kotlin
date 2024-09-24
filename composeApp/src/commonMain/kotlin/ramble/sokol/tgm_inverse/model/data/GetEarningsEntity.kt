package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class GetEarningsEntity(
    val statusCode: Int? = null,
    val reward: Long? = null,
    val completedAt: String? = null,
    val startedAt: String? = null,
    val completed: Boolean? = null,
)
