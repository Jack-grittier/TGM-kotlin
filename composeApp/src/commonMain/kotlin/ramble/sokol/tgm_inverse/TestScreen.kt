package ramble.sokol.tgm_inverse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import korlibs.encoding.toBase64
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.web.attributes.AutoComplete.Companion.url
import org.jetbrains.compose.web.attributes.alt
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.skia.Image
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import org.w3c.dom.HTMLAudioElement
import ramble.sokol.tgm_inverse.components.PlaylistItem
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.model.util.ApiRepository
import ramble.sokol.tgm_inverse.theme.background_splash
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.mont_regular
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TestScreen : Screen {

    private lateinit var apiRepo: ApiRepository
    private lateinit var listMusic: MutableState<List<MusicResponse>>

    @OptIn(ExperimentalEncodingApi::class)
    @Composable
    override fun Content() {

        apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()
        listMusic = remember {
            mutableStateOf(listOf())
        }

        scope.launch {
            getMusic("1", "25")
        }

        Column (
            modifier = Modifier.fillMaxSize()
        ) {

            ImageFromUrl("https://miniopridegroup.postideas.store/main/8814ad5a-aa03-46db-87fa-4731b9e99987")

            Spacer(modifier = Modifier.height(20.dp))

            AudioPlayerFromUrl("https://miniopridegroup.postideas.store/main/d3483186-91e4-4a09-8e99-fe445da9ac25")

            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (listMusic.value.size != 0) {

                    LazyRow() {
                        items(listMusic.value) { items: MusicResponse ->

                            ImageFromUrl(items.coverURL)

                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                        }
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


}