package ramble.sokol.tgm_inverse

import AnimatedArcProgressBar
import ProgressBarDemo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.inmo.micro_utils.common.toByteArray
import dev.inmo.tgbotapi.webapps.webApp
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.internal.JSJoda.DateTimeParseException
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import org.w3c.dom.HTMLAudioElement
import org.w3c.fetch.Body
import ramble.sokol.tgm_inverse.components.CurrentMusic
import ramble.sokol.tgm_inverse.components.MyRatingLeaderBoard
import ramble.sokol.tgm_inverse.components.PlaylistItem
import ramble.sokol.tgm_inverse.components.ProgressBarTasks
import ramble.sokol.tgm_inverse.components.TasksDone
import ramble.sokol.tgm_inverse.components.TasksGetPayment
import ramble.sokol.tgm_inverse.components.TasksPerform
import ramble.sokol.tgm_inverse.components.TasksPerformProgress
import ramble.sokol.tgm_inverse.model.data.GetEarningsEntity
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.model.data.UserEntityCreate
import ramble.sokol.tgm_inverse.model.util.ApiClient
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_airdrop
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.center_circle_playlist
import ramble.sokol.tgm_inverse.theme.center_circle_playlist_shadow
import ramble.sokol.tgm_inverse.theme.color_background_referal
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.get_bonuses
import tgminverse.composeapp.generated.resources.icon_finish_mining
import tgminverse.composeapp.generated.resources.icon_play_music
import tgminverse.composeapp.generated.resources.image_back_circle_playlist
import tgminverse.composeapp.generated.resources.image_back_mining
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar
import tgminverse.composeapp.generated.resources.test_photo
import kotlin.time.Duration

class MiningScreen (
    val modifier: Modifier,
    val userEntityCreate: UserEntityCreate,
    val airDropDate: String,
) : Screen {

    private lateinit var startedEarning: MutableState<Boolean>
    private lateinit var apiRepo: ApiRepository
    private lateinit var listMusic: MutableState<List<MusicResponse>>
    private lateinit var itemCount: MutableState<Int>
    private lateinit var statusCode: MutableState<Int?>
    private lateinit var playMusic: MutableState<Boolean>
    private lateinit var pauseMusic: MutableState<Boolean>
    private lateinit var currentSong: MutableState<MusicResponse?>
    private lateinit var musicAdUrl: MutableState<String?>
    private lateinit var adUrl: MutableState<String?>
    private lateinit var tessText: MutableState<String?>
    private lateinit var finishMining: MutableState<Boolean?>
    private lateinit var currentTime: MutableState<String>
    private lateinit var airDropVisible: MutableState<Boolean?>
    private lateinit var differenceInMillis: MutableState<Long>

    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()
        listMusic = remember {
            mutableStateOf(listOf())
        }

        var imageBitmapMusicAd by remember { mutableStateOf<ImageBitmap?>(null) }
        var imageBitmapAd by remember { mutableStateOf<ImageBitmap?>(null) }

        musicAdUrl = remember {
            mutableStateOf(null)
        }

        differenceInMillis = remember {
            mutableStateOf(0L)
        }

        finishMining = remember {
            mutableStateOf(false)
        }

        adUrl = remember {
            mutableStateOf(null)
        }

        playMusic = remember {
            mutableStateOf(false)
        }

        pauseMusic = remember {
            mutableStateOf(true)
        }

        currentSong = remember {
            mutableStateOf(null)
        }

        itemCount = remember {
            mutableStateOf(0)
        }

        statusCode = remember {
            mutableStateOf(null)
        }

        startedEarning = remember {
            mutableStateOf(false)
        }

        tessText= remember {
            mutableStateOf("")
        }

        airDropVisible = remember {
            mutableStateOf(false)
        }

        currentTime = remember {
            mutableStateOf("")
        }

        getCurrentUtcDateTime()

        scope.launch{
            getEarnings()
            getMusicAd()
            getAd()
            getMusic("1", "25")
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(background_screens)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            Image(
                                modifier = Modifier.fillMaxWidth(),
                                painter = painterResource(Res.drawable.image_back_mining),
                                contentDescription = "image_play_game",
                                contentScale = ContentScale.Crop
                            )

                            Box(
                                modifier = Modifier.width(300.dp).height(300.dp),
                                contentAlignment = Alignment.BottomEnd
                            ) {

                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(25.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Surface(
                                        modifier = Modifier.size(250.dp),
                                        shape = CircleShape
                                    ) {

                                        if (musicAdUrl.value == null) {

                                            Image(
                                                painter = painterResource(Res.drawable.test_photo),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )

                                        }else{

                                            LaunchedEffect(musicAdUrl.value) {
                                                // Загружаем изображение асинхронно
                                                val img = window.fetch(musicAdUrl.value)
                                                    .await()
                                                    .arrayBuffer()
                                                    .await()
                                                    .let {
                                                        makeFromEncoded(it.toByteArray())
                                                    }
                                                    .toComposeImageBitmap()
                                                imageBitmapMusicAd = img
                                            }

                                            imageBitmapMusicAd?.let {
                                                Image(
                                                    bitmap = it,
                                                    contentDescription = "",
                                                    modifier = Modifier.size(400.dp)
                                                )
                                            } ?: run {
                                            }

                                        }
                                    }

                                    Image(
                                        modifier = Modifier.width(109.dp).height(109.dp),
                                        painter = painterResource(Res.drawable.image_back_circle_playlist),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )


                                    Surface(
                                        modifier = Modifier.size(68.dp),
                                        shape = CircleShape
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .height(68.dp)
                                                .background(center_circle_playlist)
                                        )
                                    }
                                }

                                if (statusCode.value == 404) {

                                    Image(
                                        modifier = Modifier
                                            .height(86.dp)
                                            .width(86.dp)
                                            .padding(bottom = 14.dp, end = 14.dp)
                                            .clickable(
                                                onClick = {
                                                    scope.launch {
                                                        postEarnings()
                                                    }
                                                },
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ),
                                        painter = painterResource(Res.drawable.icon_play_music),
                                        contentDescription = "imageLine"
                                    )
                                }

                                if (statusCode.value == null) {

                                    if (finishMining.value == true){

                                        Image(
                                            modifier = Modifier
                                                .height(86.dp)
                                                .width(86.dp)
                                                .padding(bottom = 14.dp, end = 14.dp)
                                                .clickable(
                                                    onClick = {
                                                        scope.launch {
                                                            patchEarnings()
                                                        }
                                                    },
                                                    indication = null,
                                                    interactionSource = remember { MutableInteractionSource() }
                                                ),
                                            painter = painterResource(Res.drawable.icon_finish_mining),
                                            contentDescription = "imageLine"
                                        )

                                    }else {

                                        ProgressBarDemo()

                                    }
                                }

                            }

                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = tessText.value.toString(),
                        style = TextStyle(
                            fontSize = 32.sp,
                            lineHeight = 32.sp,
                            fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                            fontWeight = FontWeight(400),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    )

                }

                if (airDropVisible.value == true) {

                    val startInstant = Instant.parse(currentTime.value.toString())
                    val endInstant = Instant.parse(airDropDate)

                    // Вычисление разницы в миллисекундах
                    differenceInMillis.value = endInstant.toEpochMilliseconds() - startInstant.toEpochMilliseconds()

                    LaunchedEffect(Unit) {
                        while (true) {
                            differenceInMillis.value -= 1000
                            delay(1000) // Обновление каждую секунду
                        }
                    }


                    val minutes = (differenceInMillis.value / (1000 * 60)) % 60
                    val hours = (differenceInMillis.value / (1000 * 60 * 60)) % 24
                    val days = differenceInMillis.value / (1000 * 60 * 60 * 24)

                    val formattedTime = "${days.toString().padStart(2, '0')}:" +
                            "${hours.toString().padStart(2, '0')}:" +
                            "${minutes.toString().padStart(2, '0')}:"

                    Spacer(modifier = Modifier.padding(top = 17.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(background_airdrop),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 24.dp, horizontal = 10.dp),
                            text = formattedTime,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                                fontWeight = FontWeight(400),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                }

                if (adUrl.value != null) {

                    Spacer(modifier = Modifier.padding(top = 24.dp))

                    Box(
                        modifier = Modifier
                            .height(228.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(22.dp))
                    ) {

                        LaunchedEffect(adUrl.value) {
                            val img = window.fetch(adUrl.value)
                                .await()
                                .arrayBuffer()
                                .await()
                                .let {
                                    makeFromEncoded(it.toByteArray())
                                }
                                .toComposeImageBitmap()
                            imageBitmapAd = img
                        }

                        imageBitmapAd?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "Loaded image",
                                modifier = Modifier.fillMaxWidth().height(230.dp),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                        }


                    }

                }

                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    painter = painterResource(Res.drawable.image_line),
                    contentDescription = "imageLine"
                )

                Spacer(modifier = Modifier.padding(vertical = 12.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(Res.string.get_bonuses),
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(800),
                        color = Color.White,
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                if (listMusic.value.size == 0) {

                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        ProgressBarTasks()

                    }

                } else {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        itemCount.value = 0

                        LazyRow() {
                            items(listMusic.value) { items: MusicResponse ->

                                if (listMusic.value.indexOf(items) == 0) {
                                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                }

                                PlaylistItem(items) {
                                    currentSong.value = items
                                    playMusic.value = true
                                }

                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                                itemCount.value += 1

                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.padding(vertical = 8.dp))

            }

            if (playMusic.value == true) {

                Box(
                    modifier = Modifier.fillMaxSize().padding(bottom = 24.dp, start = 8.dp, end = 8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    val audioElement = remember { document.createElement("audio") as HTMLAudioElement }

                    audioElement.src = currentSong.value!!.url

                    if (pauseMusic.value) {
                        audioElement.play()
                    } else {
                        audioElement.pause()
                    }

                    CurrentMusic(
                        url = currentSong.value!!.coverURL,
                        name = currentSong.value!!.name,
                        author = currentSong.value!!.group,
                        play = pauseMusic.value
                    ){
                        pauseMusic.value = !pauseMusic.value
                    }
                }
            }

        }

    }

    fun compareDates(date1: String, date2: String): Int {
        return try {
            val instant1 = Instant.parse(date1)
            val instant2 = Instant.parse(date2)
            instant1.compareTo(instant2)
        } catch (e: DateTimeParseException) {
            0
        }
    }



    private suspend fun getEarnings(){
        val body = apiRepo.getEarnings(initData = userEntityCreate.initData)
        statusCode.value = body.statusCode
        if (statusCode.value == null) {
            val date1 = body.startedAt.toString()
            val date2 = body.completedAt.toString()

            val comparisonResult = compareDates(date1, date2)

            when {
                comparisonResult < 0 -> finishMining.value = false
                comparisonResult >= 0 -> finishMining.value = true
            }
        }

    }

    private suspend fun postEarnings(){
        val body = apiRepo.postEarnings(initData = userEntityCreate.initData)
        statusCode.value = body.statusCode
    }

    suspend fun patchEarnings(){
        val body = apiRepo.patchEarnings(userEntityCreate.initData)
        getEarnings()
    }

    private suspend fun getMusic(page: String, limit: String) {
        val body = apiRepo.getMusic(page, limit)
        listMusic.value = body
    }

    suspend fun getAd(){
        val body = apiRepo.getaAdvertisements()
        adUrl.value = body.fileURL
    }

    suspend fun getMusicAd(){
        val body = apiRepo.getMusicAdvertisements()
        if (body.musicId != null){
            getMusicById(body.musicId)
        }
    }

    suspend fun getMusicById(id: Long){
        val body = apiRepo.getMusic(id.toString())
        musicAdUrl.value = body.coverURL
    }

    fun getCurrentUtcDateTime() {
        // Получаем текущее время в UTC
        val currentInstant: Instant = Clock.System.now()
        // Преобразуем его в локальное время (UTC)
        val utcDateTime = currentInstant.toLocalDateTime(TimeZone.UTC)
        // Форматируем строку (например, "YYYY-MM-DD HH:MM:SS")
        currentTime.value = "${utcDateTime.date}T${utcDateTime.hour}:${utcDateTime.minute}:${utcDateTime.second}.000Z"

        val comparisonResult = compareDates(airDropDate, currentTime.value!!)

        when {
            comparisonResult < 0 -> {
                airDropVisible.value = false
            }
            comparisonResult >= 0 -> airDropVisible.value = true
        }
    }

}