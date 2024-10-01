package ramble.sokol.tgm_inverse.components

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import dev.inmo.micro_utils.common.toByteArray
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import ramble.sokol.tgm_inverse.model.data.MusicResponse
import ramble.sokol.tgm_inverse.theme.background_price_music
import ramble.sokol.tgm_inverse.theme.background_text_author
import ramble.sokol.tgm_inverse.theme.color_text_price_rating
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_bb_music
import tgminverse.composeapp.generated.resources.icon_bb_rating
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun PlaylistItem(
    items: MusicResponse,
    onClick: () -> Unit,
){

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(items.coverURL) {
        // Загружаем изображение асинхронно
        val img = window.fetch(items.coverURL)
            .await()
            .arrayBuffer()
            .await()
            .let {
                makeFromEncoded(it.toByteArray())
            }
            .toComposeImageBitmap()
        imageBitmap = img
    }

    Column(
        modifier = Modifier.width(136.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .height(140.dp)
                .width(136.dp)
                .clip(RoundedCornerShape(22.dp)),
        ){

            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Loaded image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

            } ?: run {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    ProgressBarTasks()
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 16.dp, topStart = 22.dp))
                    .background(background_price_music)
            ){

                Row(modifier = Modifier
                        .padding(start = 14.dp, end = 18.dp, top = 6.dp, bottom = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){

                    Image(
                        modifier = Modifier
                            .width(21.dp),
                        painter = painterResource(Res.drawable.icon_bb_music),
                        contentDescription = "icon_bb_rating"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = items.reward.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.mont_regular)),
                            fontWeight = FontWeight(700),
                            color = Color.White,
                        )
                    )

                }

            }

        }

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        Box(
            modifier = Modifier.height(35.dp)
                .padding(end = 3.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = items.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.mont_regular)),
                    fontWeight = FontWeight(700),
                    color = Color.White,
                ),
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        Text(
            text = items.group,
            style = TextStyle(
                fontSize = 10.sp,
                lineHeight = 13.sp,
                fontFamily = FontFamily(Font(Res.font.mont_regular)),
                fontWeight = FontWeight(700),
                color = background_text_author,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

    }

}