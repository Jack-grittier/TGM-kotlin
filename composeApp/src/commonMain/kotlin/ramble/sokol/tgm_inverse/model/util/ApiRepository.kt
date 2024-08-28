package ramble.sokol.tgm_inverse.model.util

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.data.UserEntityCreateResponse
import ramble.sokol.tgm_inverse.model.util.ApiClient.client

class ApiRepository {

    suspend fun createUser(userEntityCreate: UserEntityCreate) : UserEntityCreateResponse =
        client.post(ApiRoutes.CREATE_USER){
            setBody(userEntityCreate)
        }.body()


}