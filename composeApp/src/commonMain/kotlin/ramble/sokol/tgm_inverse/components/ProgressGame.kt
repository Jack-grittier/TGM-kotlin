package ramble.sokol.tgm_inverse.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant
import ramble.sokol.tgm_inverse.theme.background_progress_game
import ramble.sokol.tgm_inverse.theme.background_progress_game_false

@Composable
fun ProgressGame(progress: Float){
    val barHeight = 2.22.dp
    val filledBarHeight = 4.44.dp

    Canvas(modifier = Modifier.fillMaxWidth().height(barHeight)) {
        // Рисуем серую линию (основная)
        drawRect(
            color = background_progress_game_false,
            size = size.copy(height = barHeight.toPx())
        )

        // Рисуем желтую линию (заполненная)
        drawRect(
            color = background_progress_game,
            size = size.copy(width = size.width * progress, height = filledBarHeight.toPx()),
            topLeft = androidx.compose.ui.geometry.Offset(0f, -(filledBarHeight.toPx() - barHeight.toPx()) / 2)
        )
    }

}
@Composable
fun ProgressBarGameDemo(
    duration: Long
) : Float{

    val second = duration.toFloat() / 1.0f
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(targetValue = progress)

    val progressPlus = 1 / second

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(1000) // Задержка для анимации
            if (progress + progressPlus > 1f){
                progress = 1f
            }else {
                progress += progressPlus
            }
        }
    }

    ProgressGame(animatedProgress)

    return progress
}
