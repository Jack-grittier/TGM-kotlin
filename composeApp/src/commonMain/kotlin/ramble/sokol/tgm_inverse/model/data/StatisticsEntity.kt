package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsEntity(
    val balance: Long,
    val tracksListened: Long,
    val referralsCount: Long,
    val tasksCompleted: Long,
)
