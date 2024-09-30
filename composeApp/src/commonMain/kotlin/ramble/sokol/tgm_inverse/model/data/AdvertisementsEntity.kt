package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class AdvertisementsEntity(
    val id: String? = null,
    val duration: DurationAdEntity? = null,
    val fileURL: String? = null,
    val statusCode: Int? = null,
)
