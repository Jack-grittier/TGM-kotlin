package ramble.sokol.tgm_inverse.model.data

import kotlinx.serialization.Serializable

@Serializable
data class AdvertisementsEntity(
    val id: String?,
    val duration: DurationAdEntity?,
    val fileURL: String?,
    val statusCode: Int?,
)

@Serializable
data class DurationAdEntity(
    val start: String,
    val end: String,
)
