package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.inmo.micro_utils.common.toByteArray
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.web.dom.Col
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import ramble.sokol.tgm_inverse.MainMenuScreen
import ramble.sokol.tgm_inverse.theme.background_line_item
import ramble.sokol.tgm_inverse.theme.background_text_author
import ramble.sokol.tgm_inverse.theme.color_background_referal
import ramble.sokol.tgm_inverse.theme.color_text_price_rating
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_arrow_to_game
import tgminverse.composeapp.generated.resources.icon_bb_rating
import tgminverse.composeapp.generated.resources.icon_home_onboarding
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.music_pause
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun CurrentMusic(
    url: String,
    name: String,
    author: String,
    play:Boolean,
    onClick: () -> Unit,
){

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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color_background_referal)
            .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(6.dp)),
            ) {

                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Loaded image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                } ?: run {
                }
            }

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    )
                )

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(
                    text = author,
                    style = TextStyle(
                        fontSize = 10.sp,
                        lineHeight = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = background_text_author,
                    )
                )

            }

        }

        Image(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .clickable {
            onClick()
        },
        painter = painterResource(Res.drawable.music_pause),
        contentDescription = "imageLine",
        contentScale = ContentScale.Crop
        )

    }

}