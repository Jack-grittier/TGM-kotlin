package ramble.sokol.tgm_inverse.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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