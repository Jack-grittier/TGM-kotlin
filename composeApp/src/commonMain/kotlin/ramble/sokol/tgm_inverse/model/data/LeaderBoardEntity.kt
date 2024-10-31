package ramble.sokol.tgm_inverse.model.data

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardEntity(
    val position: Long? = null,
    val username: String? = null,
    val amount: Long? = null,
)
