package ramble.sokol.tgm_inverse.model.util

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
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
    ) : List<TasksMeEntity> =
        client.get(ApiRoutes.GET_TASKS_ME){
            header(HttpHeaders.Authorization, "Bearer ${initData}")
        }.body()

}