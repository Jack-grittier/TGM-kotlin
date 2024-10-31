package ramble.sokol.tgm_inverse.model.data

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardEntity(
    val position: Long,
    val username: String,
    val amount: Long,
)
