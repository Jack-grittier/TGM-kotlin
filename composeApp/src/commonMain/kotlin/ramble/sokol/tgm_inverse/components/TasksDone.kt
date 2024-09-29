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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.inmo.micro_utils.common.toByteArray
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import ramble.sokol.tgm_inverse.model.data.TasksMeEntity
import ramble.sokol.tgm_inverse.theme.background_line_item
import ramble.sokol.tgm_inverse.theme.background_line_item_white
import ramble.sokol.tgm_inverse.theme.color_active
import tgminverse.composeapp.generated.resources.PressStart2P_Regular
import tgminverse.composeapp.generated.resources.Res
import tgminverse.composeapp.generated.resources.icon_active
import tgminverse.composeapp.generated.resources.mont_regular
import tgminverse.composeapp.generated.resources.tasks_navbar
import tgminverse.composeapp.generated.resources.test_photo

@Composable
fun TasksDone(
    tasks: TasksMeEntity
) {

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(tasks.task.iconURL) {
        // Загружаем изображение асинхронно
        val img = window.fetch(tasks.task.iconURL)
            .await()
            .arrayBuffer()
            .await()
            .let {
                makeFromEncoded(it.toByteArray())
            }
            .toComposeImageBitmap()
        imageBitmap = img
    }

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(background_line_item),
        contentAlignment = Alignment.TopCenter
    ){

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){

                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Loaded image",
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp),
                        contentScale = ContentScale.Crop
                    )

                } ?: run {
                    ProgressBarTasks()
                }

                Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                Text(
                    text = tasks.task.description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.mont_regular)),
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    )
                )

            }

            Spacer(modifier = Modifier.padding(top = 18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .clip(RoundedCornerShape(27.dp))
                    .background(background_line_item_white)
            )

            Spacer(modifier = Modifier.padding(top = 18.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Received +${tasks.task.reward} BB",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.PressStart2P_Regular)),
                    fontWeight = FontWeight(400),
                    color = color_active,
                    textAlign = TextAlign.Center,
                )
            )

        }

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

    }

}