
package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorContent
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetValue
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.inmo.tgbotapi.webapps.WebAppUser
import dev.inmo.tgbotapi.webapps.webApp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.w3c.dom.HTMLAudioElement
import ramble.sokol.tgm_inverse.components.ButtonClose
import ramble.sokol.tgm_inverse.components.ButtonDisconnect
import ramble.sokol.tgm_inverse.components.CurrentMusic
import ramble.sokol.tgm_inverse.components.TextToast
import ramble.sokol.tgm_inverse.model.data.ListensMusicEntityEntity
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.data.UserEntityCreateResponse
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_line_item
import ramble.sokol.tgm_inverse.theme.background_line_item_white
import ramble.sokol.tgm_inverse.theme.background_navbar
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_splash
import ramble.sokol.tgm_inverse.theme.background_wallet_item
import ramble.sokol.tgm_inverse.theme.color_active
import ramble.sokol.tgm_inverse.theme.icon_navbar
import ramble.sokol.tgm_inverse.theme.text_navbar
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.active
import tgminverse.composeapp.generated.resources.close
import tgminverse.composeapp.generated.resources.disconnect
import tgminverse.composeapp.generated.resources.icon_active
import tgminverse.composeapp.generated.resources.icon_bb
import tgminverse.composeapp.generated.resources.icon_liderboard
import tgminverse.composeapp.generated.resources.icon_line_wallet
import tgminverse.composeapp.generated.resources.icon_logo_splash_screen
import tgminverse.composeapp.generated.resources.icon_mining
import tgminverse.composeapp.generated.resources.icon_musicality
import tgminverse.composeapp.generated.resources.icon_tasks
import tgminverse.composeapp.generated.resources.image_background_splash_screen
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.liderboard
import tgminverse.composeapp.generated.resources.mining_navbar
import tgminverse.composeapp.generated.resources.mont_bold
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.mont_semibold
import tgminverse.composeapp.generated.resources.musicality_navbar
import tgminverse.composeapp.generated.resources.tasks_navbar
import tgminverse.composeapp.generated.resources.test_photo


class MainMenuScreen(
    val userEntityCreate: UserEntityCreate,
    val bodyUserCreate: UserEntityCreateResponse,
    val selItem: Int,
    var musicPlay: MusicResponse?
) : Screen {

    private var clickSheet: MutableState<Int> = mutableIntStateOf(0)
    private lateinit var apiRepo: ApiRepository
    private lateinit var balance: MutableState<Long?>
    private lateinit var navigator: Navigator
    private lateinit var walletLock: MutableState<Boolean?>
    private lateinit var statisticLock: MutableState<Boolean?>
    private lateinit var dateAirDrop: MutableState<String?>
    private lateinit var dateMiniGame: MutableState<String?>
    private lateinit var audioElement: MutableState<HTMLAudioElement?>
    private lateinit var currentTimeMusic: MutableState<Double>
    private lateinit var playMusic: MutableState<Boolean>
    private lateinit var finishAlreadyMusic: MutableState<Boolean>
    private lateinit var statusCodeFinish: MutableState<Int?>
    private lateinit var pauseMusic: MutableState<Boolean>


    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()

        balance = remember {
            mutableStateOf(0)
        }

        pauseMusic = remember {
            mutableStateOf(true)
        }


        playMusic = remember {
            mutableStateOf(false)
        }

        finishAlreadyMusic = remember {
            mutableStateOf(false)
        }

        statusCodeFinish = remember {
            mutableStateOf(null)
        }

        statisticLock = remember {
            mutableStateOf(false)
        }

        walletLock = remember {
            mutableStateOf(false)
        }

        dateAirDrop = remember {
            mutableStateOf(null)
        }

        currentTimeMusic = remember {
            mutableStateOf(0.0)
        }

        audioElement = remember {
            mutableStateOf(null)
        }

        dateMiniGame = remember {
            mutableStateOf(null)
        }

        var selectedItem by rememberSaveable {
            mutableIntStateOf(selItem)
        }

        if (clickSheet.value == 1) {
            bottomSheet()
        }

        navigator = LocalNavigator.current!!

        if (musicPlay == null){
            audioElement.value = null
        }

        if (musicPlay != null){
            playMusic.value = true
        }

        scope.launch{
            getBalance()
            getSettings()
        }

        DisposableEffect(Unit) {
            // Код, который выполнится при закрытии экрана
            onDispose {
                audioElement.value?.pause()
            }
        }

        if (finishAlreadyMusic.value) {
            LaunchedEffect(Unit) {
                delay(3000)
                finishAlreadyMusic.value = false
            }
        }


            if (dateAirDrop.value != null && dateMiniGame.value != null) {

            Scaffold(
                modifier = Modifier.background(background_screens),
                topBar = {
                    topBar()
                },
                bottomBar = {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        if (musicPlay != null) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(bottom = 24.dp, start = 8.dp, end = 8.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {

                                LaunchedEffect(musicPlay) {
                                    if (musicPlay != null) {
                                        // Останавливаем текущую песню, если она есть
                                        audioElement.value?.pause()
                                        audioElement.value?.currentTime = 0.0

                                        audioElement.value =
                                            document.createElement("audio") as HTMLAudioElement
                                        audioElement.value!!.src = musicPlay!!.url

                                        audioElement.value!!.ontimeupdate = {
                                            audioElement.value!!.currentTime?.let {
                                                currentTimeMusic.value = it
                                            }
                                        }

                                        audioElement.value!!.onended = {
                                            scope.launch {
                                                postListensMusic(
                                                    (musicPlay!!.duration).toLong(),
                                                    musicPlay!!.id!!
                                                )
                                                musicPlay = null
                                                playMusic.value = false
                                                pauseMusic.value = true
                                                if (statusCodeFinish.value == null) {
                                                    balance.value =
                                                        balance.value!! + musicPlay!!.reward

                                                } else {
                                                    finishAlreadyMusic.value = true
                                                }
                                            }
                                        }

                                        if (pauseMusic.value) {
                                            audioElement.value!!.play()
                                        }
                                    }
                                }


                                LaunchedEffect(pauseMusic.value) {
                                    if (audioElement != null) {
                                        if (pauseMusic.value) {
                                            audioElement.value!!.play()
                                        } else {
                                            audioElement.value!!.pause()
                                        }
                                    }
                                }


                                CurrentMusic(
                                    url = musicPlay!!.coverURL,
                                    name = musicPlay!!.name,
                                    author = musicPlay!!.group,
                                    play = pauseMusic.value,
                                    duration = musicPlay!!.duration
                                ) {
                                    pauseMusic.value = !pauseMusic.value
                                }
                            }

                        }

                        if (finishAlreadyMusic.value) {

                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {

                                TextToast("The award has already been received for this song")

                            }

                        }

                        NavigationBar(
                            modifier = Modifier
                                .background(background_screens)
                                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                                .fillMaxWidth()
                                .height(87.dp),
                            containerColor = background_navbar
                        ) {

                            NavigationBarItem(
                                //modifier = Modifier.padding(start = 20.dp),
                                selected = selectedItem == 0,
                                onClick = {
                                    selectedItem = 0
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        painter = painterResource(Res.drawable.icon_musicality),
                                        contentDescription = "Musicality",
                                        tint = if (selectedItem == 0) Color.White else icon_navbar
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(Res.string.musicality_navbar),
                                        color = if (selectedItem == 0) Color.White else text_navbar,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                            fontWeight = FontWeight(700)
                                        )
                                    )
                                },
                                colors = androidx.compose.material3.NavigationBarItemDefaults
                                    .colors(
                                        indicatorColor = Color.Transparent
                                    )
                            )

                            NavigationBarItem(
                                selected = selectedItem == 1,
                                onClick = {
                                    selectedItem = 1
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier
                                            .width(33.dp),
                                        painter = painterResource(Res.drawable.icon_mining),
                                        contentDescription = "Mining",
                                        tint = if (selectedItem == 1) Color.White else icon_navbar
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(Res.string.mining_navbar),
                                        color = if (selectedItem == 1) Color.White else text_navbar,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                            fontWeight = FontWeight(700)
                                        )
                                    )
                                },
                                colors = androidx.compose.material3.NavigationBarItemDefaults
                                    .colors(
                                        indicatorColor = Color.Transparent
                                    )
                            )

                            NavigationBarItem(
                                selected = selectedItem == 2,
                                onClick = {
                                    selectedItem = 2
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier
                                            .width(20.dp)
                                            .height(20.dp),
                                        painter = painterResource(Res.drawable.icon_tasks),
                                        contentDescription = "Tasks",
                                        tint = if (selectedItem == 2) Color.White else icon_navbar
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(Res.string.tasks_navbar),
                                        color = if (selectedItem == 2) Color.White else text_navbar,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                            fontWeight = FontWeight(700)
                                        )
                                    )
                                },
                                colors = androidx.compose.material3.NavigationBarItemDefaults
                                    .colors(
                                        indicatorColor = Color.Transparent
                                    )
                            )

                            NavigationBarItem(
                                //modifier = Modifier.padding(end = 20.dp),
                                selected = selectedItem == 3,
                                onClick = {
                                    selectedItem = 3
                                },
                                icon = {
                                    Icon(
                                        modifier = Modifier
                                            .height(20.dp),
                                        painter = painterResource(Res.drawable.icon_liderboard),
                                        contentDescription = "Liderboard",
                                        tint = if (selectedItem == 3) Color.White else icon_navbar
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(Res.string.liderboard),
                                        color = if (selectedItem == 3) Color.White else text_navbar,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                            fontWeight = FontWeight(700)
                                        )
                                    )
                                },
                                colors = androidx.compose.material3.NavigationBarItemDefaults
                                    .colors(
                                        indicatorColor = Color.Transparent
                                    )
                            )

                        }

                    }
                }
            ) { innerPadding ->
                when (selectedItem) {

                    0 -> Navigator(
                        MusicalityScreen(
                            modifier = Modifier.padding(PaddingValues(top = innerPadding.calculateTopPadding())),
                            userEntityCreate,
                            dateMiniGame.value!!,
                            bodyUserCreate
                        )
                    )

                    1 -> Navigator(
                        MiningScreen(
                            modifier = Modifier.padding(PaddingValues(top = innerPadding.calculateTopPadding())),
                            userEntityCreate,
                            dateAirDrop.value!!,
                            balance.value!!,
                            bodyUserCreate,
                        )
                    )

                    2 -> Navigator(
                        TasksScreen(
                            modifier = Modifier.padding(PaddingValues(top = innerPadding.calculateTopPadding())),
                            userEntityCreate
                        )
                    )

                    3 -> Navigator(
                        LiderboardScreen(
                            modifier = Modifier.padding(PaddingValues(top = innerPadding.calculateTopPadding())),
                            userEntityCreate
                        )
                    )
//                0 -> Navigator(MusicalityScreen(modifier = Modifier.padding(innerPadding)))
//                1 -> Navigator(MiningScreen(modifier = Modifier.padding(innerPadding)))
//                2 -> Navigator(TasksScreen(modifier = Modifier.padding(innerPadding)))
//                3 -> Navigator(LiderboardScreen(modifier = Modifier.padding(innerPadding)))
                }


            }
        }

    }

    inline fun <reified T : Any> jsObject(builder: T.() -> Unit): T =
        (js("{}") as T).apply(builder)

    @Composable
    fun bottomSheet(){

//        val options = jsObject<TonConnectUIOptions>{
//            manifestUrl = ""
//        }
//
//        val tonConnectUI = TonConnectUI(options)
//        tonConnectUI.renderWalletSelector("#wallet-selector")

        val scope = rememberCoroutineScope()
        val sheetState = rememberFlexibleBottomSheetState(
            flexibleSheetSize = FlexibleSheetSize(fullyExpanded = 0.9f),
            isModal = true,
            skipSlightlyExpanded = false,
        )

        FlexibleBottomSheet(
            sheetState = sheetState,
            containerColor = background_wallet_item,
            onDismissRequest = {
                clickSheet.value = 0
            },
            dragHandle = {

                Box(
                    modifier = Modifier.padding(top = 12.dp)
                ){
                    Box(
                        modifier = Modifier
                            .width(62.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(27.dp))
                            .background(background_line_item)
                    )
                }

            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(top = 17.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.tasks_navbar),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                )

                Spacer(modifier = Modifier.padding(top = 17.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .clip(RoundedCornerShape(27.dp))
                        .background(background_line_item_white)
                )

                Spacer(modifier = Modifier.padding(top = 17.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(background_line_item)
                ){

                    Row (
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){

                        Text(
                            modifier = Modifier.weight(1f),
                            text = "UQAd...dddddduzsW",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 21.sp,
                                fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                fontWeight = FontWeight(700),
                                color = Color.White,
                            )
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(
                                text = stringResource(Res.string.active),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp,
                                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                    fontWeight = FontWeight(700),
                                    color = color_active,
                                )
                            )

                            Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                            Image(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                painter = painterResource(Res.drawable.icon_active),
                                contentDescription = "iconActive"
                            )

                        }

                    }

                }

                Spacer(modifier = Modifier.padding(top = 36.dp))

                ButtonDisconnect(
                    text = stringResource(Res.string.disconnect)
                ){

                }

                Spacer(modifier = Modifier.padding(top = 8.dp))

                ButtonClose(
                    text = stringResource(Res.string.close)
                ){
                    scope.launch {
                        sheetState.hide()
                        clickSheet.value = 0
                    }
                }

            }

        }
    }

    @Composable
    fun topBar(){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(background_screens)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row (
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(Res.drawable.test_photo),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

//                    AsyncImage(
//                        model = webAppUser.photoUrl,
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop,
//                        imageLoader = ImageLoader(null)
//                    )

//                    KamelImage(
//                        resource = asyncPainterResource(data = webAppUser.photoUrl!!),
//                        contentDescription = "description"
//                    )

                }

                Spacer(modifier = Modifier.padding(start = 6.dp))

                Text(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                if (statisticLock.value == true) {
                                    audioElement.value?.pause()
                                    navigator?.push(OnBoardingScreen(userEntityCreate, bodyUserCreate))
                                }
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    text = "@${userEntityCreate.username.toString()}",
                    //text = testText.value,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 21.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
            }

            Box (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(background_wallet_item)
                    .clickable(
                        onClick = {
                            if (walletLock.value == true) {
                                clickSheet.value = 1
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ){

                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){



                    Text(
                        text = balance.value.toString(),
                        style = TextStyle(
                            fontSize =
                            if (balance.value.toString().length <= 7) 16.sp
                            else if (balance.value.toString().length > 15) 15.sp
                            else if (balance.value.toString().length > 13) 14.sp
                            else if (balance.value.toString().length >11) 13.sp
                            else if (balance.value.toString().length > 9) 12.sp
                            else if (balance.value.toString().length > 7) 11.sp
                            else 10.sp,
                            lineHeight = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                            fontWeight = FontWeight(400),
                            color = Color.White,
                        )
                    )

                    Image(
                        modifier = Modifier
                            .height(8.dp)
                            .padding(horizontal = 8.dp)
                            .clickable(
                                onClick = {
                                    if (walletLock.value == true) {
                                        clickSheet.value = 1
                                    }
                                },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        painter = painterResource(Res.drawable.icon_line_wallet),
                        contentDescription = "imageLine"
                    )

                    Image(
                        modifier = Modifier
                            .width(48.dp)
                            .padding(bottom = 2.dp)
                            .clickable(
                                onClick = {
                                    if (walletLock.value == true) {
                                        clickSheet.value = 1
                                    }
                                },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        painter = painterResource(Res.drawable.icon_bb),
                        contentDescription = "+bb"
                    )

                }

            }

        }

    }

    suspend fun getBalance(){
        val body = apiRepo.getBalance(userEntityCreate.initData)
        balance.value = body.balance
    }

    suspend fun getSettings(){
        val body = apiRepo.getSettings(userEntityCreate.initData, "1", "25")
        for (i in body){

            when (i.key){
                "ExternalWallet.Unlocked" -> walletLock.value = i.value.toBoolean()
                "Statistics.Unlocked" -> statisticLock.value = i.value.toBoolean()
                "Drop.Date" -> dateAirDrop.value = i.value
                "MiniGame.OpenDate" -> dateMiniGame.value = i.value
            }

        }
    }

    private suspend fun postListensMusic(
        duration: Long,
        id: Long
    ){
        val listensMusicEntityEntity = ListensMusicEntityEntity(musicId = id, listeningTime = duration)
        val body = apiRepo.postListensMusic(initData = userEntityCreate.initData, listensMusicEntityEntity = listensMusicEntityEntity)
        statusCodeFinish.value = body.statusCode
    }

}
