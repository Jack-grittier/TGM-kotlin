package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class TasksEntity(
    val id: Long,
    val description: String,
    val reward: Long,
    val iconURL: String,
    val url: String,
)
