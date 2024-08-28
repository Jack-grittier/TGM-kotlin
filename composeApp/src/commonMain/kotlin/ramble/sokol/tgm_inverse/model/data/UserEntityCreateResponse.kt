package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class UserEntityCreateResponse(
    val id: Long? = null,
    val username: String? = null,
    val description: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: String? = null,
    val isPremium: Boolean? = null,
    val photoURL: String? = null,
    val updatedAt: String? = null,
    val createdAt: String? = null,
)
