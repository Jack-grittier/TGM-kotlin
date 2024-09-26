package ramble.sokol.tgm_inverse.model.util

object ApiRoutes {

    private const val BASE_URL:String = "https://pridegroup.postideas.store"
    var GET_SPLASH_SCREEN = "$BASE_URL/splash-icons"
    var CREATE_USER = "$BASE_URL/auth/login"
    var GET_MUSIC = "$BASE_URL/music"
    var GET_TASKS_ME = "$BASE_URL/users-tasks/@me"
    var GET_EARNINGS = "$BASE_URL/earnings/@me"
    var GET_BALANCE = "$BASE_URL/bills/internal/@me"
}