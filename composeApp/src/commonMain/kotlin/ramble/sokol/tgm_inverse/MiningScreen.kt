package ramble.sokol.tgm_inverse

import AnimatedArcProgressBar
import ProgressBarDemo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
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
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_airdrop
import ramble.sokol.tgm_inverse.theme.background_screens
import ramble.sokol.tgm_inverse.theme.center_circle_playlist
import ramble.sokol.tgm_inverse.theme.center_circle_playlist_shadow
import ramble.sokol.tgm_inverse.theme.color_background_referal
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.get_bonuses
import tgminverse.composeapp.generated.resources.icon_play_music
import tgminverse.composeapp.generated.resources.image_back_circle_playlist
import tgminverse.composeapp.generated.resources.image_back_mining
import tgminverse.composeapp.generated.resources.image_line
import tgminverse.composeapp.generated.resources.image_play_game
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar
import tgminverse.composeapp.generated.resources.test_photo

class MiningScreen (
    val modifier: Modifier,
    val userEntityCreate: UserEntityCreate
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
                                                    contentDescription = "Loaded image",
                                                    modifier = Modifier.size(400.dp)
                                                )
                                            } ?: run {
                                                Text("Loading image...")
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
                                            .clickable {
                                                scope.launch {
                                                    postEarnings()
                                                }
                                            },
                                        painter = painterResource(Res.drawable.icon_play_music),
                                        contentDescription = "imageLine"
                                    )
                                }

                                if (statusCode.value == null) {

                                    ProgressBarDemo()

                                }

                            }

                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "23489789",
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
                        text = "Airdrop: 52:12:56",
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

    private suspend fun getEarnings(){
        val body = apiRepo.getEarnings(initData = userEntityCreate.initData)
        statusCode.value = body.statusCode
    }

    private suspend fun postEarnings(){
        val body = apiRepo.postEarnings(initData = userEntityCreate.initData)
        statusCode.value = body.statusCode
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

}