package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class TasksMeEntityNew(
    val NotCompleted: List<TasksMeEntity>?,
    val Pending: List<TasksMeEntity>?,
    //val CompletedWithoutReceivingReward: List<TasksMeEntity>?,
    val Completed: List<TasksMeEntity>?,
)
