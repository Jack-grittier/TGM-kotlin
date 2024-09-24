package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class TasksMeEntityNew(
    val NotCompleted: TasksMeEntity,
    val Pending: TasksMeEntity,
    val CompletedWithoutReceivingReward: TasksMeEntity,
    val Completed: TasksMeEntity,
)
