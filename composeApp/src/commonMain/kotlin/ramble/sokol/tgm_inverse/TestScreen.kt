package ramble.sokol.tgm_inverse

import ProgressBarDemo
import ProgressBarDemo2
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import dev.inmo.micro_utils.common.toByteArray
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import korlibs.encoding.toBase64
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.internal.JSJoda.DateTimeParseException
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.web.attributes.AutoComplete.Companion.url
import org.jetbrains.compose.web.attributes.alt
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.skia.Image
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import org.koin.core.module._scopedInstanceFactory
import org.w3c.dom.HTMLAudioElement
import ramble.sokol.tgm_inverse.components.CurrentMusic
import ramble.sokol.tgm_inverse.components.PlaylistItem
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.util.ApiClient
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_splash
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.get_bonuses
import tgminverse.composeapp.generated.resources.icon_finish_mining
import tgminverse.composeapp.generated.resources.icon_play_music
import tgminverse.composeapp.generated.resources.mont_regular
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TestScreen : Screen {

    private lateinit var apiRepo: ApiRepository
    private lateinit var listMusic: MutableState<List<MusicResponse>>
//    private lateinit var getTime: MutableState<String>
//    private lateinit var getTime2: MutableState<String>
    private lateinit var isPlaying: MutableState<Boolean>
    private lateinit var currentSong: MutableState<MusicResponse?>
    private lateinit var isVisible: MutableState<Boolean>

    private lateinit var completedTimeMining: MutableState<String>
    private lateinit var startedTimeMining: MutableState<String>
    private lateinit var finishMining: MutableState<Boolean?>
    private lateinit var currentTime: MutableState<String>
    private lateinit var statusCode: MutableState<Int?>
    private lateinit var finish: MutableState<Boolean>
    private lateinit var scope: CoroutineScope
    private lateinit var testText: MutableState<String?>

    @OptIn(ExperimentalEncodingApi::class)
    @Composable
    override fun Content() {



        apiRepo = ApiRepository()
        scope  = rememberCoroutineScope()
        listMusic = remember {
            mutableStateOf(listOf())
        }

        testText = remember {
            mutableStateOf(null)
        }

        finish = remember {
            mutableStateOf(false)
        }

        finishMining = remember {
            mutableStateOf(false)
        }

        currentTime = remember {
            mutableStateOf("")
        }

        completedTimeMining = remember {
            mutableStateOf("")
        }

        startedTimeMining = remember {
            mutableStateOf("")
        }

        statusCode = remember {
            mutableStateOf(null)
        }

        getCurrentUtcDateTime()

        scope.launch{
            getEarnings()
        }

        isPlaying = remember {
            mutableStateOf(false)
        }

        isVisible = remember {
            mutableStateOf(false)
        }

        currentSong = remember {
            mutableStateOf(null)
        }

//        var audioElement = remember { document.createElement("audio") as HTMLAudioElement }
//
////        getTime = remember {
////            mutableStateOf("PEMIS")
////        }
////
////        getTime2 = remember {
////            mutableStateOf("PEMIS2")
////        }
//
//        fun playSong(url: String) {
//            if (audioElement != null) {
//                audioElement?.pause()
//            }
//
//            audioElement = org.w3c.dom.Audio(url).apply {
//                play()
//                onended = {
//                }
//            }
//
//            isPlaying.value = true
//        }
//
//        fun pauseSong() {
//            audioElement?.pause()
//            isPlaying.value = false
//        }
//
//
//        scope.launch {
//            getMusic("1", "25")
//        }
//
//        //getCurrentUtcDateTime()
//
//        Column (
//            modifier = Modifier.fillMaxSize()
//        ) {
//
////            Text(
////                modifier = Modifier.padding(horizontal = 16.dp),
////                text = getTime.value,
////                style = TextStyle(
////                    fontSize = 22.sp,
////                    lineHeight = 22.sp,
////                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
////                    fontWeight = FontWeight(800),
////                    color = Color.Black,
////                )
////            )
////
////            Text(
////                modifier = Modifier.padding(horizontal = 16.dp),
////                text = getTime2.value,
////                style = TextStyle(
////                    fontSize = 22.sp,
////                    lineHeight = 22.sp,
////                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
////                    fontWeight = FontWeight(800),
////                    color = Color.Black,
////                )
////            )
//
//            LazyRow() {
//                items(listMusic.value) { items: MusicResponse ->
//
//                    if (listMusic.value.indexOf(items) == 0) {
//                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
//                    }
//
//                    PlaylistItem(items) {
//                        currentSong.value = items
//                        isPlaying.value = true
//                        isVisible.value = true
//                    }
//
//                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
//
//                }
//            }
//
//            if (isVisible.value == true) {
//
//                Box(
//                    modifier = Modifier.fillMaxSize().padding(bottom = 24.dp, start = 8.dp, end = 8.dp),
//                    contentAlignment = Alignment.BottomCenter
//                ) {
//
//                    audioElement.src = currentSong.value!!.url
//
//                    if (isPlaying.value) {
//                        audioElement.play()
//                    } else {
//                        audioElement.pause()
//                    }
//
//                    CurrentMusic(
//                        url = currentSong.value!!.coverURL,
//                        name = currentSong.value!!.name,
//                        author = currentSong.value!!.group,
//                        play = isPlaying.value
//                    ){
//                        if (isPlaying.value) (pauseSong()) else audioElement?.play()
//                    }
//                }
//            }
//
//        }

        Column(
            modifier = Modifier.fillMaxSize().background(background_splash)
        ) {

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = finishMining.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = currentTime.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = statusCode.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = completedTimeMining.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = startedTimeMining.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = testText.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(800),
                    color = Color.White,
                )
            )

            if (finish.value == true) {

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

                    if (finishMining.value == true) {

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

                    } else {
                        ProgressBarDemo(
                            start = startedTimeMining.value,
                            compl = completedTimeMining.value,
                            current = currentTime.value
                        )
                    }
                }

            }
        }

    }


    @Composable
    fun AudioPlayerFromUrl(url: String) {
        var isPlaying by remember { mutableStateOf(false) }
        val audioElement = remember { document.createElement("audio") as HTMLAudioElement }

        audioElement.src = url

        Button(onClick = {
            isPlaying = !isPlaying
            if (isPlaying) {
                audioElement.play()
            } else {
                audioElement.pause()
            }
        }) {
            Text(if (isPlaying) "Pause" else "Play")
        }
    }

    @Composable
    fun ImageFromUrl(url: String) {
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(url) {
            // Загружаем изображение асинхронно
            val img = window.fetch(url)
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
                modifier = Modifier.size(400.dp)
            )
        } ?: run {
            Text("Loading image...")
        }
    }


    private suspend fun getMusic(page: String, limit: String) {
        val body = apiRepo.getMusic(page, limit)
        listMusic.value = body

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
        val body = apiRepo.getEarnings(initData = "query_id=AAEXG48VAwAAABcbjxWiXQe1&user=%7B%22id%22%3A6804151063%2C%22first_name%22%3A%22%D0%90%D1%80%D1%82%D1%91%D0%BC%22%2C%22last_name%22%3A%22%22%2C%22username%22%3A%22RambleNeTOT%22%2C%22language_code%22%3A%22ru%22%2C%22allows_write_to_pm%22%3Atrue%7D&auth_date=1724088134&hash=a87fd616ea66d7832f5ace4da0be88ae3e7538d63ad3459a559da2fc191028e6")
        statusCode.value = body.statusCode
        testText.value = body.toString()
        if (body.statusCode == null) {
            completedTimeMining.value = body.completedAt.toString()
            startedTimeMining.value = body.startedAt.toString()
            val date2 = body.completedAt.toString()
            val date1 = currentTime.value
            val comparisonResult = compareDates(date1, date2)
            testText.value = comparisonResult.toString()
            when {
                comparisonResult < 0 -> finishMining.value = false
                comparisonResult >= 0 -> finishMining.value = true
            }
        }
        finish.value = true
    }

    fun getCurrentUtcDateTime() {
        // Получаем текущее время в UTC
        val currentInstant: Instant = Clock.System.now()
        // Преобразуем его в локальное время (UTC)
        val utcDateTime = currentInstant.toLocalDateTime(TimeZone.UTC)
        // Форматируем строку (например, "YYYY-MM-DD HH:MM:SS")
        currentTime.value = "${utcDateTime.date}T${utcDateTime.hour.toString().padStart(2, '0')}:${utcDateTime.minute.toString().padStart(2, '0')}:${utcDateTime.second.toString().padStart(2, '0')}.000Z"
    }

    private suspend fun postEarnings(){
        val body = apiRepo.postEarnings(initData = "query_id=AAEXG48VAwAAABcbjxWiXQe1&user=%7B%22id%22%3A6804151063%2C%22first_name%22%3A%22%D0%90%D1%80%D1%82%D1%91%D0%BC%22%2C%22last_name%22%3A%22%22%2C%22username%22%3A%22RambleNeTOT%22%2C%22language_code%22%3A%22ru%22%2C%22allows_write_to_pm%22%3Atrue%7D&auth_date=1724088134&hash=a87fd616ea66d7832f5ace4da0be88ae3e7538d63ad3459a559da2fc191028e6")
        getEarnings()
    }

    suspend fun patchEarnings(){
        val body = apiRepo.patchEarnings(initData = "query_id=AAEXG48VAwAAABcbjxWiXQe1&user=%7B%22id%22%3A6804151063%2C%22first_name%22%3A%22%D0%90%D1%80%D1%82%D1%91%D0%BC%22%2C%22last_name%22%3A%22%22%2C%22username%22%3A%22RambleNeTOT%22%2C%22language_code%22%3A%22ru%22%2C%22allows_write_to_pm%22%3Atrue%7D&auth_date=1724088134&hash=a87fd616ea66d7832f5ace4da0be88ae3e7538d63ad3459a559da2fc191028e6")
        getEarnings()
    }

}