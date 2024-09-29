package ramble.sokol.tgm_inverse.model.util

import dev.inmo.tgbotapi.types.queryField
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import ramble.sokol.tgm_inverse.model.data.AdvertisementsEntity
import ramble.sokol.tgm_inverse.model.data.BalanceEntity
import ramble.sokol.tgm_inverse.model.data.DurationAdEntity
import ramble.sokol.tgm_inverse.model.data.GetEarningsEntity
import ramble.sokol.tgm_inverse.model.data.LeaderBoardEntity
import ramble.sokol.tgm_inverse.model.data.LeaderboardReferalEntity
import ramble.sokol.tgm_inverse.model.data.MusicAdEntity
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.data.SplashIconEntity
import ramble.sokol.tgm_inverse.model.data.StatisticsEntity
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.model.data.TasksMeEntityNew
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.data.UserEntityCreateResponse
import ramble.sokol.tgm_inverse.model.util.ApiClient.client

class ApiRepository {

    suspend fun createUser(userEntityCreate: UserEntityCreate) : UserEntityCreateResponse =
    client.post(ApiRoutes.CREATE_USER){
        setBody(userEntityCreate)
    }.body()

    suspend fun getTasksMe(
        initData: String
    ) : TasksMeEntityNew =
        client.get(ApiRoutes.GET_TASKS_ME){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

    suspend fun getSplashIcon(
    ) : List<SplashIconEntity> =
        client.get(ApiRoutes.GET_SPLASH_SCREEN){
            url {
                parameters.append("enabled", true.toString())
            }
        }.body()

    suspend fun getMusic(
        page: String,
        limit: String,
    ) : List<MusicResponse> =
        client.get(ApiRoutes.GET_MUSIC){
            url {
                parameters.append("page", page)
                parameters.append("limit", limit)
                parameters.append("sortField", "name")
                parameters.append("sortOrder", "ASC")
            }
        }.body()

    suspend fun getEarnings(
        initData: String
    ) : GetEarningsEntity =
        client.get(ApiRoutes.GET_EARNINGS){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

    suspend fun postEarnings(
        initData: String
    ) : GetEarningsEntity =
        client.post(ApiRoutes.GET_EARNINGS){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

    suspend fun getBalance(
        initData: String
    ) : BalanceEntity =
        client.get(ApiRoutes.GET_BALANCE){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

    suspend fun getLeaderboard(
        page: String,
        limit: String,
    ) : List<LeaderBoardEntity> =
        client.get(ApiRoutes.GET_LEADERBOARD){
            url {
                parameters.append("page", page)
                parameters.append("limit", limit)
            }
        }.body()

    suspend fun getUserStatistics(
        initData: String
    ) : StatisticsEntity =
        client.get(ApiRoutes.GET_STATISTICS){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

    suspend fun getaAdvertisements() : AdvertisementsEntity =
        client.get(ApiRoutes.GET_ADVERTISEMENTS).body()

    suspend fun getMusicAdvertisements() : MusicAdEntity =
        client.get(ApiRoutes.GET_MUSIC_ADVERTISEMENTS).body()

    suspend fun getLeaderBoardReferal(
        initData: String,
        page: String,
        limit: String
    ) : List<LeaderboardReferalEntity> =
        client.get(ApiRoutes.GET_LEADERBOARD_REFERAL){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
            url {
                parameters.append("page", page)
                parameters.append("limit", limit)
            }
        }.body()

    suspend fun getMyPositionLeaderBoard(
        initData: String
    ) : LeaderBoardEntity =
        client.get(ApiRoutes.GET_LEADERBOARD_MY_POSITION){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

    suspend fun getMusic(
        id: String
    ) : MusicResponse =
        client.get("${ApiRoutes.GET_MUSIC}/{$id}").body()

}