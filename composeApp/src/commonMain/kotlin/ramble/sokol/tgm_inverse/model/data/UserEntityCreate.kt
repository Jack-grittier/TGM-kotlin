package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntityCreate(
    //@SerialName("phone_number")
    val initData: String,
    //@SerialName("firstname")
    val id: Long,
    //@SerialName("lastname")
    val username: String,
    //@SerialName("password")
    val firstName: String,
    val lastName: String?,
    val languageCode: String,
    val isPremium: Boolean,
    var photoURL: String?,
    val referralCode: Long?,
)
