package ramble.sokol.tgm_inverse

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.w3c.dom.HTMLAudioElement
import ramble.sokol.tgm_inverse.components.ButtonExitGame
import ramble.sokol.tgm_inverse.components.GameBlockActive
import ramble.sokol.tgm_inverse.components.ProgressBarGameDemo
import ramble.sokol.tgm_inverse.components.ProgressGame
import ramble.sokol.tgm_inverse.components.TextRewardGame
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.data.RewardGameEntity
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.data.UserEntityCreateResponse
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_color_collect
import ramble.sokol.tgm_inverse.theme.background_line_game
import ramble.sokol.tgm_inverse.theme.background_progress_game
import ramble.sokol.tgm_inverse.theme.background_progress_game_star
import ramble.sokol.tgm_inverse.theme.background_reward_game
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.background_text_author
import ramble.sokol.tgm_inverse.theme.color_hello_peterburg
import ramble.sokol.tgm_inverse.theme.color_price_referal
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_bb_game
import tgminverse.composeapp.generated.resources.icon_bb_music
import tgminverse.composeapp.generated.resources.icon_copy_link
import tgminverse.composeapp.generated.resources.icon_music_game
import tgminverse.composeapp.generated.resources.icon_star_1
import tgminverse.composeapp.generated.resources.icon_star_1_true
import tgminverse.composeapp.generated.resources.icon_star_2
import tgminverse.composeapp.generated.resources.icon_star_2_true
import tgminverse.composeapp.generated.resources.icon_star_3
import tgminverse.composeapp.generated.resources.icon_star_3_true
import tgminverse.composeapp.generated.resources.image_back_game
import tgminverse.composeapp.generated.resources.image_center_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.image_verticall_line_game
import tgminverse.composeapp.generated.resources.mont_regular
import kotlin.random.Random

class GameScreen(
    val userEntityCreate: UserEntityCreate,
    val bodyUserCreate: UserEntityCreateResponse
) : Screen {

    private val startPosition = mutableStateOf(getRandomNumber())
    private val lastPosition = mutableStateOf(0)
    private val nextPosition = mutableStateOf(0)
    private lateinit var apiRepo: ApiRepository
    private lateinit var musicGame: MutableState<MusicResponse?>
    private lateinit var musicPlay: MutableState<Boolean>
    private lateinit var gameFinish: MutableState<Boolean>
    private lateinit var audioElement: MutableState<HTMLAudioElement?>
    private lateinit var currentTimeMusic: MutableState<Double>
    private lateinit var finishRequests: MutableState<Boolean>
    private lateinit var navigator: Navigator
    private lateinit var musicName: MutableState<String?>
    private lateinit var durationMusicSecond: MutableState<Long?>
    private lateinit var testText: MutableState<String>
    private lateinit var clickStart: MutableState<Int>
    private lateinit var finishBox1: MutableState<Boolean>
    private lateinit var finishBox2: MutableState<Boolean>
    private lateinit var finishBox3: MutableState<Boolean>
    private lateinit var finishBox4: MutableState<Boolean>
    private lateinit var starCcount: MutableState<Int>
    private lateinit var rewardMusic: MutableState<Long>
    private lateinit var rewardMusicCurrent: MutableState<Long>
    private lateinit var musicId: MutableState<Long?>
    private lateinit var rareFinish: MutableState<Boolean>

    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()

        navigator = LocalNavigator.current!!

        finishRequests = remember {
            mutableStateOf(false)
        }

        rareFinish = remember {
            mutableStateOf(false)
        }

        rewardMusic = remember {
            mutableStateOf(0L)
        }

        rewardMusicCurrent = remember {
            mutableStateOf(0L)
        }

        starCcount = remember {
            mutableStateOf(0)
        }

        testText = remember {
            mutableStateOf("")
        }

        finishBox1 = remember {
            mutableStateOf(false)
        }

        finishBox2 = remember {
            mutableStateOf(false)
        }

        finishBox3 = remember {
            mutableStateOf(false)
        }

        musicId = remember {
            mutableStateOf(null)
        }

        finishBox4 = remember {
            mutableStateOf(false)
        }

        clickStart = remember {
            mutableStateOf(0)
        }

        musicName = remember {
            mutableStateOf(null)
        }

        musicGame = remember {
            mutableStateOf(null)
        }

        musicPlay = remember {
            mutableStateOf(false)
        }

        gameFinish = remember {
            mutableStateOf(false)
        }

        audioElement = remember {
            mutableStateOf(null)
        }

        currentTimeMusic = remember {
            mutableStateOf(0.0)
        }

        durationMusicSecond = remember {
            mutableStateOf(0L)
        }

        if (finishRequests.value == false) {
            scope.launch {
                getMusicGame()
                finishRequests.value = true
            }
        }

        var speed by remember { mutableStateOf(0.2f) }

        var offsetY by remember { mutableStateOf(0.dp) }

        val animatedOffsetY by animateDpAsState(
            targetValue = offsetY,
            animationSpec = tween(durationMillis = (1000 / speed).toInt())
        )

        var isBoxVisible by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition = updateTransition(targetState = isBoxVisible, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        var boxOffsetY = transition.animateDp(
            transitionSpec = { tween(durationMillis =
            if (isBoxVisible) 1
            else (1000 / speed).toInt()
            ) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        var isBoxVisible2 by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition2 = updateTransition(targetState = isBoxVisible2, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        var boxOffsetY2 = transition2.animateDp(
            transitionSpec = { tween(durationMillis =
            if (isBoxVisible2) 1
            else (1000 / speed).toInt()
            ) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        var isBoxVisible3 by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition3 = updateTransition(targetState = isBoxVisible3, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        var boxOffsetY3 = transition3.animateDp(
            transitionSpec = { tween(durationMillis =
            if (isBoxVisible3) 1
            else (1000 / speed).toInt()
            ) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        var isBoxVisible4 by remember { mutableStateOf(true) }

        // Определяем анимацию перемещения
        val transition4 = updateTransition(targetState = isBoxVisible4, label = "Box Transition")

        // Определяем начальную и конечную позиции для анимации
        var boxOffsetY4 = transition4.animateDp(
            transitionSpec = { tween(durationMillis =
            if (isBoxVisible4) 1
            else (1000 / speed).toInt()
            ) },
            label = "Box Offset Y"
        ) { state ->
            if (state) -200.dp else (1000.dp) // Начальная позиция за верхней границей
        }

        if (boxOffsetY4.value  >= 900.dp){
            lastPosition.value = 0
            finishBox4.value = true
            isBoxVisible4 = true
            if (nextPosition.value == 4){
                nextPosition.value = 0
                rareFinish.value = true
                audioElement.value?.pause()
                scope.launch {
                    postRewardGame(musicId.value!!, currentTimeMusic.value.toLong())
                    navigator.push(FinishGameScreen(
                        userEntityCreate, bodyUserCreate, musicName.value!!, rewardMusicCurrent.value, starCcount.value
                    ))
                }
            }
        }
        if (boxOffsetY3.value  >= 900.dp){
            lastPosition.value = 0
            finishBox3.value = true
            isBoxVisible3 = true
            if (nextPosition.value == 3){
                nextPosition.value = 0
                rareFinish.value = true
                audioElement.value?.pause()
                scope.launch {
                    postRewardGame(musicId.value!!, currentTimeMusic.value.toLong())
                    navigator.push(FinishGameScreen(
                        userEntityCreate, bodyUserCreate, musicName.value!!, rewardMusicCurrent.value, starCcount.value
                    ))
                }
            }
        }
        if (boxOffsetY2.value  >= 900.dp){
            lastPosition.value = 0
            finishBox2.value = true
            isBoxVisible2 = true
            if (nextPosition.value == 2){
                nextPosition.value = 0
                rareFinish.value = true
                audioElement.value?.pause()
                scope.launch {
                    postRewardGame(musicId.value!!, currentTimeMusic.value.toLong())
                    navigator.push(FinishGameScreen(
                        userEntityCreate, bodyUserCreate, musicName.value!!, rewardMusicCurrent.value, starCcount.value
                    ))
                }
            }
        }
        if (boxOffsetY.value >= 900.dp){
            lastPosition.value = 0
            finishBox1.value = true
            isBoxVisible = true
            if (nextPosition.value == 1){
                nextPosition.value = 0
                rareFinish.value = true
                audioElement.value?.pause()
                scope.launch {
                    postRewardGame(musicId.value!!, currentTimeMusic.value.toLong())
                    navigator.push(FinishGameScreen(
                        userEntityCreate, bodyUserCreate, musicName.value!!, rewardMusicCurrent.value, starCcount.value
                    ))
                }
            }
        }

        var progress by remember { mutableStateOf(0f) }

        if (progress >= 0.32f){
            starCcount.value = 1
        }
        if(progress >= 0.63f) {
            starCcount.value = 2
        }
        if (progress >= 0.99f){
            starCcount.value = 3
        }
        if (musicPlay.value == true) {

            LaunchedEffect(musicGame) {



                if (musicGame != null) {
                    // Останавливаем текущую песню, если она есть
                    audioElement.value?.pause()
                    audioElement.value?.currentTime = 0.0

                    audioElement.value = document.createElement("audio") as HTMLAudioElement
                    audioElement.value!!.src = musicGame.value!!.url

                    audioElement.value!!.onloadedmetadata = {
                        // Получаем полную длительность песни
                        audioElement.value!!.duration.toLong().also { durationMusicSecond.value = it }
                    }


                    audioElement.value!!.ontimeupdate = {
                        audioElement.value!!.currentTime?.let {
                            currentTimeMusic.value = it
                        }
                    }

                    audioElement.value!!.onended = {
                        audioElement.value?.pause()
                        if (rareFinish.value == false) {
                            scope.launch {
                                postRewardGame(musicId.value!!, musicGame.value!!.duration.toLong())
                                navigator.push(
                                    FinishGameScreen(
                                        userEntityCreate,
                                        bodyUserCreate,
                                        musicName.value!!,
                                        rewardMusicCurrent.value,
                                        starCcount.value
                                    )
                                )
                            }
                        }
                    }

                    if (musicPlay.value) {
                        audioElement.value!!.play()
                    }
                }
            }


            LaunchedEffect(musicPlay.value) {
                if (audioElement != null) {
                    if (musicPlay.value) {
                        audioElement.value!!.play()
                    } else {
                        audioElement.value!!.pause()
                    }
                }
            }

            if (durationMusicSecond.value!! > 0){
                val secondPlus = (durationMusicSecond.value!!/15)*1000
                LaunchedEffect(Unit) {
                    while (speed < 0.35f) {
                        delay(secondPlus) // Задержка для анимации
                        speed += 0.01f
                    }
                }
            }

        }

        if (finishRequests.value == true) {

            Box(
                modifier = Modifier.fillMaxSize()
                    .background(background_screens),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(Res.drawable.image_center_line),
                    contentDescription = null
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {

                        if (startPosition.value == 1) {

                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(
                                    modifier = Modifier.offset(y = animatedOffsetY)
                                ) {
                                    GameBlockActive(
                                        startActive = if (clickStart.value == 1) false else true,
                                        backgroundClick = if (clickStart.value == 1) true else false
                                    ) {
                                        musicPlay.value = true
                                        offsetY = 1000.dp
                                        clickStart.value = 1
                                        lastPosition.value = startPosition.value
                                        nextPosition.value = getRandomNumber(lastPosition.value)
                                        when (nextPosition.value) {
                                            2 -> isBoxVisible2 = false
                                            3 -> isBoxVisible3 = false
                                            4 -> isBoxVisible4 = false
                                        }
                                    }
                                }
                            }

                        }

                        if (nextPosition.value == 1 || lastPosition.value == 1) {

                            Box(
                                modifier = Modifier.offset(y = boxOffsetY.value)
                            ) {
                                GameBlockActive(
                                    startActive = false,
                                    backgroundClick = if (lastPosition.value == 1) true else false
                                ) {
                                    when (lastPosition.value){
                                        1-> {
                                            finishBox1.value = true
                                            isBoxVisible = true
                                        }
                                        2-> {
                                            finishBox2.value = true
                                            isBoxVisible2 = true
                                        }
                                        3-> {
                                            finishBox3.value = true
                                            isBoxVisible3 = true
                                        }
                                        4-> {
                                            finishBox4.value = true
                                            isBoxVisible4 = true
                                        }
                                    }
                                    val lastik = lastPosition.value
                                    lastPosition.value = nextPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value, lastik)
                                    when (nextPosition.value) {
                                        1 -> isBoxVisible = true
                                        2 -> isBoxVisible2 = false
                                        3 -> isBoxVisible3 = false
                                        4 -> isBoxVisible4 = false
                                    }
                                }
                            }

                        }

                    }

                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        painter = painterResource(Res.drawable.image_verticall_line_game),
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {

                        if (startPosition.value == 2) {

                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(
                                    modifier = Modifier.offset(y = animatedOffsetY)
                                ) {
                                    GameBlockActive(
                                        startActive = if (clickStart.value == 2) false else true,
                                        backgroundClick = if (clickStart.value == 2) true else false
                                    ) {
                                        musicPlay.value = true
                                        offsetY = 1000.dp
                                        clickStart.value = 2
                                        lastPosition.value = startPosition.value
                                        nextPosition.value = getRandomNumber(lastPosition.value)
                                        when (nextPosition.value) {
                                            1 -> isBoxVisible = false
                                            3 -> isBoxVisible3 = false
                                            4 -> isBoxVisible4 = false
                                        }
                                    }
                                }
                            }

                        }

                        if (nextPosition.value == 2 || lastPosition.value == 2) {

                            Box(
                                modifier = Modifier.offset(y = boxOffsetY2.value)
                            ) {
                                GameBlockActive(
                                    startActive = false,
                                    backgroundClick = if (lastPosition.value == 2) true else false
                                ) {
                                    when (lastPosition.value){
                                        1-> {
                                            finishBox1.value = true
                                            isBoxVisible = true
                                        }
                                        2-> {
                                            finishBox2.value = true
                                            isBoxVisible2 = true
                                        }
                                        3-> {
                                            finishBox3.value = true
                                            isBoxVisible3 = true
                                        }
                                        4-> {
                                            finishBox4.value = true
                                            isBoxVisible4 = true
                                        }
                                    }
                                    val lastik = lastPosition.value
                                    lastPosition.value = nextPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value, lastik)
                                    when (nextPosition.value) {
                                        1 -> isBoxVisible = false
                                        2 -> isBoxVisible2 = true
                                        3 -> isBoxVisible3 = false
                                        4 -> isBoxVisible4 = false
                                    }
                                }
                            }

                        }

                    }

                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        painter = painterResource(Res.drawable.image_verticall_line_game),
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {

                        if (startPosition.value == 3) {

                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(
                                    modifier = Modifier.offset(y = animatedOffsetY)
                                ) {
                                    GameBlockActive(
                                        startActive = if (clickStart.value == 3) false else true,
                                        backgroundClick = if (clickStart.value == 3) true else false
                                    ) {
                                        musicPlay.value = true
                                        offsetY = 1000.dp
                                        clickStart.value = 3
                                        lastPosition.value = startPosition.value
                                        nextPosition.value = getRandomNumber(lastPosition.value)
                                        when (nextPosition.value) {
                                            1 -> isBoxVisible = false
                                            2 -> isBoxVisible2 = false
                                            4 -> isBoxVisible4 = false
                                        }
                                    }
                                }
                            }

                        }

                        if (nextPosition.value == 3 || lastPosition.value == 3) {

                            Box(
                                modifier = Modifier.offset(y = boxOffsetY3.value)
                            ) {
                                GameBlockActive(
                                    startActive = false,
                                    backgroundClick = if (lastPosition.value == 3) true else false
                                ) {
                                    when (lastPosition.value){
                                        1-> {
                                            finishBox1.value = true
                                            isBoxVisible = true
                                        }
                                        2-> {
                                            finishBox2.value = true
                                            isBoxVisible2 = true
                                        }
                                        3-> {
                                            finishBox3.value = true
                                            isBoxVisible3 = true
                                        }
                                        4-> {
                                            finishBox4.value = true
                                            isBoxVisible4 = true
                                        }
                                    }
                                    val lastik = lastPosition.value
                                    lastPosition.value = nextPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value, lastik)
                                    when (nextPosition.value) {
                                        1 -> isBoxVisible = false
                                        2 -> isBoxVisible2 = false
                                        3 -> isBoxVisible3 = true
                                        4 -> isBoxVisible4 = false
                                    }
                                }
                            }

                        }

                    }

                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        painter = painterResource(Res.drawable.image_verticall_line_game),
                        contentDescription = null
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {

                        if (startPosition.value == 4) {

                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(
                                    modifier = Modifier.offset(y = animatedOffsetY)
                                ) {
                                    GameBlockActive(
                                        startActive = if (clickStart.value == 4) false else true,
                                        backgroundClick = if (clickStart.value == 4) true else false
                                    ) {
                                        musicPlay.value = true
                                        offsetY = 1000.dp
                                        clickStart.value = 4
                                        lastPosition.value = startPosition.value
                                        nextPosition.value = getRandomNumber(lastPosition.value)
                                        when (nextPosition.value) {
                                            1 -> isBoxVisible = false
                                            2 -> isBoxVisible2 = false
                                            3 -> isBoxVisible3 = false
                                        }
                                    }
                                }
                            }

                        }

                        if (nextPosition.value == 4 || lastPosition.value == 4) {

                            Box(
                                modifier = Modifier.offset(y = boxOffsetY4.value)
                            ) {
                                GameBlockActive(
                                    startActive = false,
                                    backgroundClick = if (lastPosition.value == 4) true else false
                                ) {
                                    when (lastPosition.value){
                                        1-> {
                                            finishBox1.value = true
                                            isBoxVisible = true
                                        }
                                        2-> {
                                            finishBox2.value = true
                                            isBoxVisible2 = true
                                        }
                                        3-> {
                                            finishBox3.value = true
                                            isBoxVisible3 = true
                                        }
                                        4-> {
                                            finishBox4.value = true
                                            isBoxVisible4 = true
                                        }
                                    }
                                    val lastik = lastPosition.value
                                    lastPosition.value = nextPosition.value
                                    nextPosition.value = getRandomNumber(lastPosition.value, lastik)
                                    when (nextPosition.value) {
                                        1 -> isBoxVisible = false
                                        2 -> isBoxVisible2 = false
                                        3 -> isBoxVisible3 = false
                                        4 -> isBoxVisible4 = true
                                    }
                                }
                            }

                        }

                    }


                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 11.dp, horizontal = 20.dp),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(103.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 120.dp,
                                    topEnd = 120.dp,
                                    bottomEnd = 19.dp,
                                    bottomStart = 19.dp
                                )
                            )
                            .background(color_hello_peterburg),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(background_reward_game),
                                contentAlignment = Alignment.Center
                            ) {

                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    if (musicPlay.value == true && durationMusicSecond.value!! != 0L && rewardMusic.value != 0L){
                                        //testText.value = durationMusicSecond.value!!.toString()
                                        rewardMusicCurrent.value = TextRewardGame(rewardMusic.value, durationMusicSecond.value!!)
                                    }

                                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                                    Image(
                                        modifier = Modifier.width(24.dp),
                                        painter = painterResource(Res.drawable.icon_bb_game),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )

                                }

                            }

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(modifier = Modifier.padding(horizontal = 42.dp)) {

                                    if (musicPlay.value == true && durationMusicSecond.value!! != 0L){
                                        //testText.value = durationMusicSecond.value!!.toString()
                                        progress = ProgressBarGameDemo(durationMusicSecond.value!!)
                                    }

                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(start = 40.dp, end = 25.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .width(9.dp)
                                            .height(9.dp)
                                            .clip(RoundedCornerShape(25.dp))
                                            .background(background_progress_game)
                                    )

                                    Image(
                                        painter = painterResource(
                                            if (progress >= 0.32f) Res.drawable.icon_star_1_true
                                            else Res.drawable.icon_star_1
                                        ),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )

                                    Image(
                                        painter = painterResource(
                                            if (progress >= 0.63f) Res.drawable.icon_star_2_true
                                            else Res.drawable.icon_star_2
                                        ),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                    )

                                    Image(
                                        painter = painterResource(
                                            if (progress >= 0.99f) Res.drawable.icon_star_3_true
                                            else Res.drawable.icon_star_3
                                        ),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )

                                }


                            }

                        }



                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(Res.drawable.image_back_game),
                            contentDescription = "image_game",
                            contentScale = ContentScale.Crop
                        )

                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 32.dp, horizontal = 20.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                modifier = Modifier.height(24.dp).width(24.dp),
                                painter = painterResource(Res.drawable.icon_music_game),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {

                                Text(
                                    text = "Song:",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                        fontWeight = FontWeight(700),
                                        color = background_color_collect,
                                    )
                                )

                                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                                Text(
                                    text = musicName.value!!,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                                        fontWeight = FontWeight(700),
                                        color = background_color_collect,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )


                            }

                        }

                        Box(modifier = Modifier.weight(0.7f)) {

                            ButtonExitGame("Выйти") {
                                audioElement.value?.pause()
                                navigator.push(MainMenuScreen(
                                    userEntityCreate, bodyUserCreate, 0, null
                                ))
                            }
                        }
                    }
                }
            }
        }
    }

    fun getRandomNumber() : Int{
        val randomNumber = Random.nextInt(1, 5)
        return randomNumber
    }

    fun getRandomNumber(excludedNumber: Int): Int {
        val numbers = (1..4).filter { it != excludedNumber }
        return numbers[Random.nextInt(numbers.size)]
    }

    fun getRandomNumber(exclude1: Int, exclude2: Int): Int {
        val possibleNumbers = (1..4).filter { it != exclude1 && it != exclude2 }
        return possibleNumbers.random()
    }

    suspend fun getMusicGame(){
        val body = apiRepo.getMusicGame()
        musicGame.value = body
        musicName.value = body.name
        rewardMusic.value = body.reward
        musicId.value = body.id
    }

    suspend fun postRewardGame(musicId: Long, second: Long){
        val body = apiRepo.postReawardGame(
            rewardGameEntity = RewardGameEntity(
                musicId = musicId,
                seconds = second
            ),
            initData = userEntityCreate.initData
        )
        rewardMusicCurrent.value = body
    }

}