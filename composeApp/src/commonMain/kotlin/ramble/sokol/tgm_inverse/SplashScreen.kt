package ramble.sokol.tgm_inverse

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.inmo.tgbotapi.requests.bot.GetMyCommands.Companion.scope
import dev.inmo.tgbotapi.webapps.WebAppUser
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.web.dom.Main
import ramble.sokol.tgm_inverse.theme.background_splash
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_logo_splash_screen
import tgminverse.composeapp.generated.resources.image_background_splash_screen
import tgminverse.composeapp.generated.resources.mont_regular
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.navigator.Navigator
import dev.inmo.micro_utils.common.toByteArray
import dev.inmo.tgbotapi.webapps.webApp
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.await
import kotlinx.coroutines.withContext
import kotlinx.datetime.internal.JSJoda.use
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import ramble.sokol.tgm_inverse.model.data.SplashIconEntity
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.data.UserEntityCreateResponse
import ramble.sokol.tgm_inverse.model.util.ApiRepository


class SplashScreen : Screen {

    private lateinit var initData: MutableState<String>
    private lateinit var loading: MutableState<Boolean>
    private lateinit var userData: MutableState<WebAppUser?>
    private lateinit var apiRepo: ApiRepository
    private lateinit var navigator: Navigator
    private lateinit var body: MutableState<UserEntityCreateResponse?>
    private lateinit var user: MutableState<UserEntityCreate?>
    private lateinit var userName: MutableState<String?>
    private lateinit var userUrl: MutableState<String?>
    private lateinit var id: MutableState<String?>
    private lateinit var firstname: MutableState<String?>
    private lateinit var lastname: MutableState<String?>
    private lateinit var languageCode: MutableState<String?>
    private lateinit var isPremium: MutableState<Boolean?>
    private lateinit var referalCode: MutableState<Long?>
    private lateinit var splashIocns: MutableState<List<SplashIconEntity>>

    @Composable
    override fun Content() {

        val scope  = rememberCoroutineScope()
        apiRepo = ApiRepository()

        splashIocns = remember {
            mutableStateOf(listOf())
        }

        initData = remember {
            mutableStateOf("")
        }

        loading = remember {
            mutableStateOf(false)
        }

        userData = remember {
            mutableStateOf(null)
        }

        body = remember {
            mutableStateOf(null)
        }

        user = remember {
            mutableStateOf(null)
        }

        userName = remember {
            mutableStateOf(null)
        }

        userUrl = remember {
            mutableStateOf(null)
        }

        id = remember {
            mutableStateOf(null)
        }

        firstname = remember {
            mutableStateOf(null)
        }

        lastname = remember {
            mutableStateOf(null)
        }

        languageCode = remember {
            mutableStateOf(null)
        }

        isPremium = remember {
            mutableStateOf(null)
        }

        referalCode = remember {
            mutableStateOf(null)
        }

        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        navigator = LocalNavigator.current!!

        scope.launch {
            getIcon()
        }

            val transition = rememberInfiniteTransition(label = "")
            val alpha by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 3000
                    ),
                ), label = ""
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background_splash)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){

//                Image(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    painter = painterResource(Res.drawable.image_background_splash_screen),
//                    contentDescription = "imageSplashScreen"
//                )

                    Image(
                        modifier = Modifier
                            .height(51.dp)
                            .fillMaxWidth()
                        .alpha(alpha = alpha),
                        painter = painterResource(Res.drawable.icon_logo_splash_screen),
                        contentDescription = "imageSplashScreen"
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 53.dp, horizontal = 24.dp),
                    contentAlignment = Alignment.BottomCenter
                ){

                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = splashIocns.value[0].toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                            fontWeight = FontWeight(700),
                            color = Color.White,
                        )
                    )

                    if (splashIocns.value.size != 0) {

                        LaunchedEffect(splashIocns.value[0].fileURL) {
                            val img = window.fetch(splashIocns.value[0].fileURL)
                                .await()
                                .arrayBuffer()
                                .await()
                                .let {
                                    makeFromEncoded(it.toByteArray())
                                }
                                .toComposeImageBitmap()
                            imageBitmap = img
                        }

                        imageBitmap?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "Loaded image",
                                modifier = Modifier.height(45.dp),
                                contentScale = ContentScale.Crop
                            )

                        } ?: run {}

                    }

                }

            }

        LaunchedEffect(
            key1 = true
        ) {
            delay(3000L)

            //navigator?.push(TestScreen())

            scope.launch {
                initData.value = webApp.initData
                userUrl.value = webApp.initDataUnsafe.user!!.photoUrl
                userName.value = webApp.initDataUnsafe.user!!.username
                id.value = webApp.initDataUnsafe.user!!.id.toString()
                firstname.value = webApp.initDataUnsafe.user!!.firstName
                lastname.value = webApp.initDataUnsafe.user!!.lastName
                languageCode.value = webApp.initDataUnsafe.user!!.languageCode.toString()
                isPremium.value = webApp.initDataUnsafe.user!!.is_premium
                val ref = webApp.initDataUnsafe.startParam
                if (ref != null){
                    referalCode.value = ref.toString().toLong()
                }
                if (isPremium.value == null){
                    isPremium.value = false
                }
                val userEntityCreate = UserEntityCreate(
                    initData = initData.value,
                    id = id.value.toString().toLong(),
                    username = userName.value.toString(),
                    firstName = firstname.value.toString(),
                    lastName = lastname.value.toString(),
                    languageCode = languageCode.value.toString(),
                    isPremium = isPremium.value!!,
                    photoURL = userUrl.value,
                    referralCode = referalCode.value
                )
                body.value = apiRepo.createUser(userEntityCreate)
                loading.value = true
                if (body.value!!.error == null) {
                    navigator?.push(MainMenuScreen(userEntityCreate, body.value!!))
                    //navigator.push(MiningScreen(Modifier, userEntityCreate))
                    //navigator.push(OnBoardingScreen(userEntityCreate, body.value!!))
                }else {

                }
                //navigator?.push(MainMenuScreen())
            }

        }

    }

    private suspend fun getIcon() {

        val body = apiRepo.getSplashIcon()
        splashIocns.value = body

    }

}