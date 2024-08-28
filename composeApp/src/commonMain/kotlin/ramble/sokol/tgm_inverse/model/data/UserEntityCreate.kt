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
    val description: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val languageCode: String,
    val isPremium: Boolean,
    val photoURL: String,
    val referralCode: Long? = null,
)
